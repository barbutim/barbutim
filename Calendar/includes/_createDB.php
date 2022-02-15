<?php   // Vytvorenie "first" databázy
    $note = array(
        'id' => uniqid(),
        'date' => '2021-1-1',
        'time' => '00:00'
        'title' => 'Test',
        'text' => 'This is a test.'
    );

    $data = array(
        'notes' => array($note)
    );
    
    $encoded = json_encode($data);
    file_put_contents(DBFILE, $encoded);
?>