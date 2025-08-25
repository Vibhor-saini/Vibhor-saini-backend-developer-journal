<?php
include('connection.php');
date_default_timezone_set('UTC'); // Change timezone if needed
$logFile = __DIR__ . '/ship_product_log.txt'; // Log file path

$logPath1 = __DIR__ . '/ship_product_log.txt';
        file_put_contents($logPath1, "working! for shipproduct products!");
        
header('Content-Type: application/json');

$token = '';
$baseUrl = 'https://api.shipbob.com/2.0/inventory-level/locations';

$filteredResults = [];
$nextCursor = null;

do {
    $url = $baseUrl;
    if ($nextCursor) {
        $url .= '?cursor=' . urlencode($nextCursor);
    }

    $curl = curl_init();
    curl_setopt_array($curl, [
        CURLOPT_URL => $url,
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_HTTPHEADER => [
            'Authorization: Bearer ' . $token,
            'Accept: application/json'
        ],
    ]);

    $response = curl_exec($curl);

    if (curl_errno($curl)) {
        echo json_encode(['error' => curl_error($curl)]);
        exit;
    }

    $httpCode = curl_getinfo($curl, CURLINFO_HTTP_CODE);
    curl_close($curl);

    if ($httpCode !== 200) {
        echo json_encode(['error' => "HTTP code $httpCode received."]);
        exit;
    }

    $data = json_decode($response, true);
    if (json_last_error() !== JSON_ERROR_NONE) {
        echo json_encode(['error' => json_last_error_msg()]);
        exit;
    }

    if (!isset($data['items'])) {
        echo json_encode(['error' => "Missing 'items' key in API response."]);
        exit;
    }

    foreach ($data['items'] as $item) {
        $filteredLocations = array_filter($item['locations'], function ($loc) {
            return $loc['location_id'] == 156;
        });

        if (count($filteredLocations) > 0) {
            $item['locations'] = array_values($filteredLocations);
            $filteredResults[] = $item;
        }
    }

    if (isset($data['next']) && !empty($data['next'])) {
        $parsedUrl = parse_url($data['next']);
        if (isset($parsedUrl['query'])) {
            parse_str($parsedUrl['query'], $queryParams);
            $nextCursor = $queryParams['cursor'] ?? null;
        } else {
            $nextCursor = null;
        }
    } else {
        $nextCursor = null;
    }

} while ($nextCursor);

echo "<pre>";
print_r($filteredResults);
//  Output the results
echo "Total Products Fetched: " . count($filteredResults) . "<br>";

foreach ($filteredResults as $item) {
    $sku  = $conn->real_escape_string($item['sku'] ?? '');
    $name = $conn->real_escape_string($item['name'] ?? '');
    $loc = $item['locations'][0] ?? [];

    $on_hand_quantity     = (int)($loc['on_hand_quantity'] ?? 0);
    $fulfillable_quantity = (int)($loc['fulfillable_quantity'] ?? 0);
    $committed_quantity   = (int)($loc['committed_quantity'] ?? 0);
    $awaiting_quantity    = (int)($loc['awaiting_quantity'] ?? 0);

    // Check if SKU exists
    $checkSql = "SELECT * FROM ship_product WHERE ship_sku = '$sku' LIMIT 1";
    $checkResult = $conn->query($checkSql);

    if ($checkResult && $checkResult->num_rows > 0) {
        $row = $checkResult->fetch_assoc();

        // Compare and update if any values differ
        $changes = [];
        if ((int)$row['on_hand_quantity'] !== $on_hand_quantity) {
            $changes[] = "on_hand_quantity: {$row['on_hand_quantity']} → $on_hand_quantity";
        }
        if ((int)$row['fulfillable_quantity'] !== $fulfillable_quantity) {
            $changes[] = "fulfillable_quantity: {$row['fulfillable_quantity']} → $fulfillable_quantity";
        }
        if ((int)$row['committed_quantity'] !== $committed_quantity) {
            $changes[] = "committed_quantity: {$row['committed_quantity']} → $committed_quantity";
        }
        if ((int)$row['awaiting_quantity'] !== $awaiting_quantity) {
            $changes[] = "awaiting_quantity: {$row['awaiting_quantity']} → $awaiting_quantity";
        }

        if (!empty($changes)) {
            // Perform update
            $updateSql = "
                UPDATE ship_product SET
                    name = '$name',
                    on_hand_quantity = '$on_hand_quantity',
                    fulfillable_quantity = '$fulfillable_quantity',
                    committed_quantity = '$committed_quantity',
                    awaiting_quantity = '$awaiting_quantity'
                WHERE ship_sku = '$sku'
            ";
            $conn->query($updateSql);

            // Log changes
            $timestamp = date('Y-m-d');
            $log = "[$timestamp] SKU: $sku, Name: $name | " . implode(" | ", $changes) . "\n";
            file_put_contents($logFile, $log, FILE_APPEND);

            echo "Updated: $sku<br>";
        } else {
            echo "No change: $sku<br>";
        }

    } else {
        // Insert new
        $insertSql = "
            INSERT INTO ship_product (
                name, ship_sku, on_hand_quantity,
                fulfillable_quantity, committed_quantity, awaiting_quantity
            ) VALUES (
                '$name', '$sku', '$on_hand_quantity',
                '$fulfillable_quantity', '$committed_quantity', '$awaiting_quantity'
            )
        ";
        if ($conn->query($insertSql) === TRUE) {
            echo "Inserted: $sku<br>";
        } else {
            echo "Insert error for $sku: " . $conn->error . "<br>";
        }
    }
}

// Delete products not in the API response
$allSkusFromAPI = array_map(function ($item) {
    return "'" . $item['sku'] . "'";
}, $filteredResults);

if (!empty($allSkusFromAPI)) {
    $skuList = implode(',', $allSkusFromAPI);
    $deleteSql = "DELETE FROM ship_product WHERE ship_sku NOT IN ($skuList)";
    if ($conn->query($deleteSql) === TRUE) {
        echo "Deleted products not in API response.<br>";
    } else {
        echo "Delete error: " . $conn->error . "<br>";
    }
} else {
    echo "No SKUs received from API, skipping delete operation.<br>";
}

?>
