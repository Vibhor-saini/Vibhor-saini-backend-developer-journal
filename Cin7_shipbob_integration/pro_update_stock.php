<?php
include('connection.php'); // Your DB connection file
echo "Working for US loaction!";
// Step 1: Fetch cin7_product data
$cin7Data = [];
$cin7Query = "SELECT sku, onhand, avg_cost, batch FROM cin7_product";
$cin7Result = $conn->query($cin7Query);

if ($cin7Result && $cin7Result->num_rows > 0) {
    while ($row = $cin7Result->fetch_assoc()) {
        $sku = trim($row['sku']);
        $cin7Data[$sku] = [
            'onhand' => (float)$row['onhand'],
            'avg_cost' => $row['avg_cost'],
            'batch' => $row['batch']
        ];
    }
}

// Step 2: Fetch ship_product data
$shipData = [];
$shipQuery = "SELECT ship_sku, on_hand_quantity FROM ship_product";
$shipResult = $conn->query($shipQuery);

if ($shipResult && $shipResult->num_rows > 0) {
    while ($row = $shipResult->fetch_assoc()) {
        $sku = trim($row['ship_sku']);
        $shipData[$sku] = (float)$row['on_hand_quantity'];
    }
}

// Step 3: Compare onhand vs on_hand_quantity
$diff = [];
// $testSku = 'NF-03-LI-WH';
foreach ($cin7Data as $sku => $details) {
    //  if ($sku !== $testSku) continue; 
    $cin7_onhand = $details['onhand'];
    $ship_onhand = $shipData[$sku] ?? null;

    if ($ship_onhand === null) {
        // SKU missing in ship_product
        $diff[$sku] = [
            'cin7_onhand' => $cin7_onhand,
            'ship_onhand' => 'MISSING'
        ];
    } elseif ($cin7_onhand != $ship_onhand) {
        $diff[$sku] = [
            'cin7_onhand' => $cin7_onhand,
            'ship_onhand' => $ship_onhand
        ];
    }
}

// Step 4: Display differences
if (empty($diff)) {
} else {
    echo "<h3>Unmatched Onhand Quantities:</h3><table border='1' cellpadding='5'>";
    echo "<tr><th>SKU</th><th>CIN7 Onhand</th><th>Ship Onhand</th></tr>";

    foreach ($diff as $sku => $vals) {
        echo "<tr>
                <td>{$sku}</td>
                <td>{$vals['cin7_onhand']}</td>
                <td>{$vals['ship_onhand']}</td>
              </tr>";
    }

    echo "</table>";
}



// Log file path
$logPath = __DIR__ . '/stock_adjustment_log.txt';

foreach ($diff as $sku => $quantities) {
   echo $sku = trim($sku);

    // Fetch from cin7_product
    $cin7Sql = "SELECT * FROM cin7_product WHERE sku = '$sku'";
    $cin7Result = $conn->query($cin7Sql);
    $cin7 = $cin7Result->fetch_assoc();

    // Fetch from ship_product
    $shipSql = "SELECT * FROM ship_product WHERE ship_sku = '$sku'";
    $shipResult = $conn->query($shipSql);
    $ship = $shipResult->fetch_assoc();

    if (!$cin7 || !$ship) {
        $msg = "SKU $sku not found in one or both tables.\n";
        echo $msg;
        file_put_contents($logPath, "[" . date('Y-m-d H:i:s') . "] $msg", FILE_APPEND);
        continue;
    }

    $cin7Qty = (float)$cin7['onhand'];
    $shipQty = (float)$ship['on_hand_quantity'];

    if ($cin7Qty == $shipQty) {
        $msg = "SKU $sku: Stock already matching. No adjustment needed.\n";
        echo $msg;
        file_put_contents($logPath, "[" . date('Y-m-d H:i:s') . "] $msg", FILE_APPEND);
        continue;
    }

    // Build API Payload
    $payload = [
        "EffectiveDate" => date('Y-m-d\T00:00:00'),
        "Status" => "COMPLETED",
        "Account" => "405",
        "Reference" => "Stock adjustment for $sku on " . date('Y-m-d'),
        "Lines" => [[
            "ProductID" => $cin7['pro_id'],
            "SKU" => $sku,
            "BatchSN" => $cin7['batch'],
            "ProductName" => $cin7['name'],
            "Quantity" => $shipQty,
            "UnitCost" => $cin7['avg_cost'],
            "LocationID" => "4b7032c6-53df-450a-be2a-933cb3435420",
            "Location" => "ShipBob - US",
            "ReceivedDate" => date('Y-m-d\T00:00:00')
        ]]
    ];

    $jsonPayload = json_encode($payload);

    $curl = curl_init();
    curl_setopt_array($curl, [
        CURLOPT_URL => 'https://inventory.dearsystems.com/ExternalApi/v2/StockAdjustment',
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_CUSTOMREQUEST => 'POST',
        CURLOPT_POSTFIELDS => $jsonPayload,
        CURLOPT_HTTPHEADER => [
            'api-auth-accountid: 4377c4d4-450f-46b2-97e6-70e9bc3d3816',
            'api-auth-applicationkey: 72018294-1f79-ce31-22c0-7c9f19911448',
            'Content-Type: application/json',
        ],
    ]);

    $response = curl_exec($curl);
    $error = curl_error($curl);
    curl_close($curl);

    if ($error) {
        $msg = "CURL Error for SKU $sku: $error\n";
        echo $msg;
        file_put_contents($logPath, "[" . date('Y-m-d H:i:s') . "] $msg", FILE_APPEND);
        continue;
    }

    $data = json_decode($response, true);

    // Optional: print API response
    echo "<pre>Response for $sku:\n";
    print_r($data);
    echo "</pre>";

    //Log the adjustment
    $logLine = sprintf(
        "[%s] Name: %s | SKU: %s | Previous OnHand: %.2f | New OnHand: %.2f\n",
        date('Y-m-d H:i:s'),
        $cin7['name'],
        $sku,
        $cin7Qty,
        $shipQty
    );
    file_put_contents($logPath, $logLine, FILE_APPEND);
}
?>