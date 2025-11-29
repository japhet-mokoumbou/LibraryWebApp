<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
    <div class="min-h-screen flex items-center justify-center">
        <div class="bg-white p-12 rounded-xl shadow-xl text-center">
            <h1 class="text-4xl font-bold text-green-600 mb-4">Connexion réussie !</h1>
            <p class="text-2xl">Bienvenue, ${user.firstName} ${user.lastName}</p>
            <p class="text-lg mt-2 text-indigo-600">Rôle : ${user.role.name}</p>
            <a href="books" class="mt-8 inline-block bg-indigo-600 text-white px-8 py-4 rounded-lg text-xl hover:bg-indigo-700 transform hover:scale-105 transition">
    			Voir le catalogue des livres
			</a>
        </div>
    </div>
</body>
</html>