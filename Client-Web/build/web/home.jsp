<!DOCTYPE html>
<html lang="en">
<head>
    <title>Trip-Finder</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="siteCSS.css">
</head>
<body>

    <header>
        <h1>Welcome to Trip-Finder</h1>
    </header>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            // Check if the user is logged in by checking the existence of the user ID in localStorage
            function isUserLoggedIn() {
                return localStorage.getItem('userID') !== null;
            }

            // Check if the element with data-role exists
            if (!isUserLoggedIn()) {
                var navLinks = document.querySelectorAll('.registered');
                navLinks.forEach(link => { link.style.display = 'none'; });
                // Display a message when the user is not logged in
                var notLoggedInMessage = document.createElement('p');
                notLoggedInMessage.textContent = 'You are not logged in. Please log in to access these features.';
                document.body.appendChild(notLoggedInMessage);
            } else {
                var navLinks = document.querySelectorAll('.registered');
                navLinks.forEach(link => { link.style.display = 'block'; });
            }
        });
    </script>
    
    <div class="button-container">
        <button class="registered" onclick="location.href='queryTrip.html'">Query Trip</button>
        <button class="registered" onclick="location.href='proposeTrip.html'">Propose Trip</button>
    </div>

</body>
</html>
