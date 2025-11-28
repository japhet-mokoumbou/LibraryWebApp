<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Library - Connexion</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gradient-to-br from-indigo-900 via-purple-900 to-pink-800 min-h-screen flex items-center justify-center">
    <div class="bg-white p-10 rounded-2xl shadow-2xl w-full max-w-md">
        <h1 class="text-4xl font-bold text-center text-indigo-700 mb-8">Library</h1>

        <% if (request.getAttribute("error") != null) { %>
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <form action="login" method="post" class="space-y-6">
            <div>
                <label class="block text-gray-700 font-semibold mb-2">Email</label>
                <input type="email" name="email" required
                       class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-4 focus:ring-indigo-300 outline-none transition"
                       placeholder="admin@library.com">
            </div>

            <div>
                <label class="block text-gray-700 font-semibold mb-2">Mot de passe</label>
                <input type="password" name="password" required
                       class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-4 focus:ring-indigo-300 outline-none transition"
                       placeholder="••••••••">
            </div>

            <button type="submit"
                    class="w-full bg-gradient-to-r from-indigo-600 to-purple-600 text-white font-bold py-4 rounded-lg hover:from-indigo-700 hover:to-purple-700 transform hover:scale-105 transition duration-200">
                Se connecter
            </button>
        </form>

        
    </div>
</body>
</html>