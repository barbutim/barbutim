<?php
    $users = array( // Array list užívateľov
        array(
            "id" => 1,
            "username" => "barbutim",
            "firstname" => "Timotej",
            "lastname" => "Barbuš",
            "passwordhash" => password_hash("mitubrab", PASSWORD_DEFAULT)
        ),
    );
    // Funkcia pre vrátenie všetkých hodnôt užívateľa, na základe jeho $username
    function getUserByUsername($username, $users){
        foreach($users as $user){
            if($user['username'] == $username){
                return $user;
            }
        }
        return false;
    }
?>