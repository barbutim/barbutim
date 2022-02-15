<?php
    include('includes/_users.php'); // Zalinkovanie Array listu užívateľov a funkcie
    $meno = '';

    if(isset($_POST['login'])){ // Ak užívateľ stačí tlačidlo "prihlásiť sa"...
        $meno = htmlspecialchars($_POST['meno']);
        $heslo = htmlspecialchars($_POST['heslo']);
        $username = isset($meno) ? $meno : false;
        $password = isset($heslo) ? $heslo : false;

        if($username && $password){ // Kontrola, či bolo zadané meno a heslo
            $user = getUserByUsername($username, $users);

            if($user){  // Kontrola, či zadaný užívateľ existuje
                if(password_verify($password, $user['passwordhash'])){  // Kontrola, či sa zhoduje meno a heslo s údajmi užívateľa
                    session_start();    // -> ak áno, uloží si ID do $_SESSION a pokračuje na main.php
                    $_SESSION['userID'] = $user['id'];
                    header('Location: main.php');
                }

                else{
                    $error = 'Zle zadané meno alebo heslo!';    // Chybové hlášky
                }
            }

            else{
                $error = 'Zle zadané meno alebo heslo!';
            }
        }
        
        else{
            $error = 'Nebolo zadané meno a heslo!';
        }
    }
    $mainTitle = 'Prihlásenie';
    include('includes/_header.php');    // Zalinkovanie hlavičky dokumentu
?>
<script src="javascript/login.js"></script> <!-- Kontrola prihlásenia cez JS -->
<article>
    <form method="POST">    <!-- Formulár pre prihlásenie -->
        <h2>Prihlásenie</h2>
        <div>
            <label>
                <span class="state">Meno:</span>
                <span class="warning req">required</span>
                <input type="text" id="name" name="meno" placeholder="Zadaj meno..." value="<?php echo htmlspecialchars($meno)?>" required pattern="[A-Za-z0-9\s]+" >
            </label>
        </div><div>
            <label>
                <span class="state">Heslo:</span>
                <span class="warning req">required</span>
                <input type="password" id="password" name="heslo" placeholder="Zadaj heslo..." required pattern="[A-Za-z0-9\s]+" >
            </label>
        </div>
        <input class="sendButton" type="submit" name="login" value="Prihlásiť sa">
        <?php
            echo isset($error) ? "<p class='warning'>".$error."</p>" : '';  // Zobrazenie chýbových hlášok
        ?>
    </form>
</article>
<?php
    include('includes/_footer.php');    // Zalinkovanie pätičky dokumentu
?>