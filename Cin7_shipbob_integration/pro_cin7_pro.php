<?php
include('connection.php');

// API credentials
$accountId = '';
$appKey    = '';
$location  = urlencode('ShipBob - US');

$page = 1;
$allProducts = [];

// Fetch product availability data page by page
do {
    $curl = curl_init();
    curl_setopt_array($curl, array(
        CURLOPT_URL => "https://inventory.dearsystems.com/ExternalApi/v2/ref/productavailability?Page=$page&location=$location",
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_HTTPHEADER => array(
            "api-auth-accountid: $accountId",
            "api-auth-applicationkey: $appKey",
        ),
    ));

    $response = curl_exec($curl);
    curl_close($curl);

    $data = json_decode($response, true);

    if (isset($data['ProductAvailabilityList']) && is_array($data['ProductAvailabilityList'])) {
        $allProducts = array_merge($allProducts, $data['ProductAvailabilityList']);
        $page++;
    } else {
        break;
    }
} while (!empty($data['ProductAvailabilityList']));

echo "Total Products from API: " . count($allProducts) . "<br>";

// Track SKUs from API
$apiSkus = [];

foreach ($allProducts as $product) {
    $pro_id             = $conn->real_escape_string($product['ID'] ?? '');
    $name               = $conn->real_escape_string($product['Name'] ?? '');
    $sku                = $conn->real_escape_string($product['SKU'] ?? '');
    $loc                = $conn->real_escape_string($product['Location'] ?? '');
    $onhand             = (float)($product['OnHand'] ?? 0);
    $available          = (float)($product['Available'] ?? 0);
    $on_order           = (float)($product['OnOrder'] ?? 0);
    

    $apiSkus[] = $sku;

    // Check if product exists
    $checkSql = "SELECT onhand, batch FROM cin7_product WHERE pro_id = '$pro_id' LIMIT 1";
    $checkResult = $conn->query($checkSql);

    if ($checkResult && $checkResult->num_rows > 0) {
        $existing = $checkResult->fetch_assoc();
        $existing_onhand = (float)$existing['onhand'];
        $existing_batch = $existing['batch'];

        // Update if onhand changed or batch is empty
        if ($onhand !== $existing_onhand || $existing_batch == '') {
            $updateSql = "
                UPDATE cin7_product SET
                    name = '$name',
                    sku = '$sku',
                    location = '$loc',
                    batch = '',
                    onhand = '$onhand',
                    available = '$available',
                    on_order = '$on_order'
                WHERE pro_id = '$pro_id'
            ";
            if ($conn->query($updateSql)) {
                echo "📝 Updated: $sku (onhand changed or batch added)<br>";
            } else {
                echo "Update error ($sku): " . $conn->error . "<br>";
            }
        } else {
            echo "No Change: $sku<br>";
        }
    } else {
        // Insert only if batch is 202501
        $insertSql = "
            INSERT INTO cin7_product (
                pro_id, name, sku, location, batch,
                onhand, available, on_order
            ) VALUES (
                '$pro_id', '$name', '$sku', '$loc', '202501',
                '$onhand', '$available', '$on_order'
            )
        ";
        if ($conn->query($insertSql)) {
            echo "Inserted: $sku<br>";
        } else {
            echo "Insert error ($sku): " . $conn->error . "<br>";
        }
    }
}

// Delete products with batch 202501 that are no longer in the API
$escapedSkus = array_map([$conn, 'real_escape_string'], $apiSkus);
$apiSkusList = "'" . implode("','", $escapedSkus) . "'";

$deleteQuery = "
    DELETE FROM cin7_product 
    WHERE sku NOT IN ($apiSkusList) AND batch = '202501'
";

if ($conn->query($deleteQuery)) {
    $deletedRows = $conn->affected_rows;
    echo " Deleted $deletedRows SKU(s) not found in API (batch 202501)<br>";
} else {
    echo "Delete error: " . $conn->error . "<br>";
}

$logPath = __DIR__ . '/cin7_products.txt';
        file_put_contents($logPath, "working! for cin7 products!");
        
$conn->close();
?>
