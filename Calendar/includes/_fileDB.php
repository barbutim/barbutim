<?php
    $filename = 'json/db.json';
    if(file_exists($filename)){ // Ak nie je funkčná (zalinkovaná) databáza -> zapíš do $data chybovú hlášku, ktorú následne zobrazuje namiesto údajov na main.php
        define('DBFILE', 'json/db.json');   // Príjmanie poznámok z databázy
        $data = file_get_contents(DBFILE);
        $data = json_decode($data, JSON_OBJECT_AS_ARRAY);
    }
    else{
        $data = 'Databáza nie je funkčná!';
    }
    
    /*if($data == NULL){
        include('includes/_createDB.php');
    }*/

    function storeData($data, $newNote){    // Funkcia pre zápis poznámky do databázy
        array_push($data['notes'], $newNote);
        $encoded = json_encode($data);
        file_put_contents(DBFILE, $encoded);
    }

    function deleteNote($data, $noteId){    // Funkcia pre vymazanie poznámky z databázy
        foreach($data['notes'] as $key => $note ){
            if($note['id'] == $noteId){
                unset($data['notes'][$key]);
                break;
            }
        }
        $encodedData = json_encode($data);
        file_put_contents(DBFILE, $encodedData);
    }

    function findNote($data, $noteId){  // Funkcia pre nájdenie poznámky z databázy podľa ID
        foreach($data['notes'] as $key => $note ){
            if($note['id'] == $noteId){
                return $note;
            }
        }
        return null;
    }
?>