<?php

include('connection.php'); 
$page = 1;
$allProducts = [];

$logPath = __DIR__ . '/cin7_price.txt';
        file_put_contents($logPath, "working! for cin7 price!");
        
do {
    $curl = curl_init();

    curl_setopt_array($curl, array(
        CURLOPT_URL => "https://inventory.dearsystems.com/ExternalApi/v2/product?page=$page",
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_ENCODING => '',
        CURLOPT_MAXREDIRS => 10,
        CURLOPT_TIMEOUT => 0,
        CURLOPT_FOLLOWLOCATION => true,
        CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
        CURLOPT_CUSTOMREQUEST => 'GET',
        CURLOPT_HTTPHEADER => array(
            'api-auth-accountid: ',
            'api-auth-applicationkey: ',
            'Content-Type: application/json',
        ),
    ));

    $response = curl_exec($curl);
    curl_close($curl);

    $data = json_decode($response, true);

    if (json_last_error() !== JSON_ERROR_NONE) {
        echo " JSON decode error: " . json_last_error_msg() . "<br>";
        break;
    }

    if (empty($data['Products'])) {
        break;
    }

    $allProducts = array_merge($allProducts, $data['Products']);
    echo "Page $page fetched. Products: " . count($data['Products']) . "<br>";

    $page++;
} while (true);


// Step 1: Collect all API SKUs
$apiSkus = [];
foreach ($allProducts as $product) {
    if (!empty($product['SKU'])) {
        $apiSkus[] = $conn->real_escape_string($product['SKU']);
    }
}

// Step 2: Fetch all DB SKUs
$dbSkus = [];
$result = $conn->query("SELECT sku FROM cin7_product");
while ($row = $result->fetch_assoc()) {
    $dbSkus[] = $row['sku'];
}


echo "Total SKUs in DB: " . count($dbSkus) . "<br>";
echo "Total SKUs from API: " . count($apiSkus) . "<br>";

// 🔍 Find missing SKUs (in DB but not in API)
$missingSkus = array_diff($dbSkus, $apiSkus);

echo "Missing SKUs in API (" . count($missingSkus) . "):<br>";
echo "<pre>";
print_r($missingSkus);
echo "</pre>";


// 🔍 Step 3: Find missing SKU(s)
$missingSkus = array_diff($dbSkus, $apiSkus);

if (count($missingSkus) > 0) {
    echo "<br>These SKU(s) were NOT found in API response:<br>";
    foreach ($missingSkus as $missingSku) {
        echo "$missingSku<br>";
    }
    echo "<br>Total missing: " . count($missingSkus) . "<br>";
} else {
    echo "<br>All SKUs matched between DB and API.<br>";
}



// Loop through products and update avg_cost
$updatedCount = 0;
$skippedCount = 0;

foreach ($allProducts as $product) {
    $sku = $conn->real_escape_string($product['SKU'] ?? '');
    $avgCost = $conn->real_escape_string($product['AverageCost'] ?? 0);

    if ($sku === '') {
        $skippedCount++;
        continue;
    }

    $checkSql = "SELECT id FROM cin7_product WHERE sku = '$sku' LIMIT 1";
    $checkResult = $conn->query($checkSql);

    if ($checkResult && $checkResult->num_rows > 0) {
        $updateSql = "UPDATE cin7_product SET avg_cost = '$avgCost' WHERE sku = '$sku'";
        if ($conn->query($updateSql) === TRUE) {
            echo $sku . "<br>";
            $updatedCount++;
        } else {
            echo "Update error for SKU: $sku → " . $conn->error . "<br>";
        }
    } else {
        $skippedCount++;
    }
}

echo "<br>Total Updated: $updatedCount<br>";
echo "Total Skipped or Not Found: $skippedCount<br>";

?>
