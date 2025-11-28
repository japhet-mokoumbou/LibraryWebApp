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
            <a href="login.jsp" class="mt-8 inline-block bg-red-600 text-white px-6 py-3 rounded-lg hover:bg-red-700">
                Déconnexion
            </a>
        </div>
    </div>
</body>
</html>