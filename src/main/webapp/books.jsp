<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Catalogue des livres</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-gray-50 min-h-screen">
    <!-- Navbar simple -->
    <nav class="bg-indigo-700 text-white shadow-lg">
        <div class="max-w-7xl mx-auto px-4 py-4 flex justify-between items-center">
            <h1 class="text-2xl font-bold">Library - Catalogue</h1>
            <div>
                <span class="mr-4">Bonjour, ${user.firstName}</span>
                <a href="login.jsp" class="bg-red-600 hover:bg-red-700 px-4 py-2 rounded">Déconnexion</a>
            </div>
        </div>
    </nav>

    <div class="max-w-7xl mx-auto px-4 py-8">
        <!-- Filtres -->
        <div class="bg-white rounded-xl shadow-md p-6 mb-8">
            <form method="get" class="grid grid-cols-1 md:grid-cols-3 gap-4">
                <input type="text" name="search" value="${search}" placeholder="Rechercher par titre ou auteur..."
                       class="px-4 py-3 border rounded-lg focus:ring-2 focus:ring-indigo-500 outline-none">
                
                <select name="category" class="px-4 py-3 border rounded-lg focus:ring-2 focus:ring-indigo-500">
                    <option value="">Toutes les catégories</option>
                    <c:forEach var="cat" items="${categories}">
                        <option value="${cat.id}" ${cat.id == selectedCategory ? 'selected' : ''}>${cat.name}</option>
                    </c:forEach>
                </select>

                <button type="submit" class="bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-3 rounded-lg">
                    <i class="fas fa-search mr-2"></i> Rechercher
                </button>
            </form>
        </div>

        <!-- Tableau des livres -->
        <div class="bg-white rounded-xl shadow-md overflow-hidden">
            <table class="w-full">
                <thead class="bg-gray-100">
                    <tr class="text-left text-gray-700 uppercase text-sm">
                        <th class="px-6 py-4">Couverture</th>
                        <th class="px-6 py-4">Titre</th>
                        <th class="px-6 py-4">Auteur</th>
                        <th class="px-6 py-4">Catégorie</th>
                        <th class="px-6 py-4 text-center">Disponibilité</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty books}">
                            <tr>
                                <td colspan="5" class="px-6 py-8 text-center text-gray-500">
                                    <i class="fas fa-book-open text-4xl mb-4 block"></i>
                                    <p class="text-lg">Aucun livre trouvé</p>
                                    <p class="text-sm mt-2">Essayez de modifier vos critères de recherche</p>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="book" items="${books}">
                                <tr class="border-t hover:bg-gray-50 transition">
                                    <td class="px-6 py-4">
                                        <div class="bg-gray-200 border-2 border-dashed rounded w-16 h-24"></div>
                                    </td>
                                    <td class="px-6 py-4 font-semibold">${book.title}</td>
                                    <td class="px-6 py-4">${book.author}</td>
                                    <td class="px-6 py-4">
                                        <span class="px-3 py-1 bg-indigo-100 text-indigo-800 rounded-full text-sm">
                                            ${book.category.name}
                                        </span>
                                    </td>
                                    <td class="px-6 py-4 text-center">
                                        <span class="inline-block px-4 py-2 text-white font-bold rounded-full text-sm ${book.availabilityClass}">
                                            ${book.availableCopies} / ${book.totalCopies}
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <!-- Pagination -->
        <c:if test="${totalPages > 1}">
            <div class="mt-8 flex justify-center space-x-2">
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <a href="?page=${i}&search=${search}&category=${selectedCategory}"
                       class="px-4 py-2 rounded ${i == currentPage ? 'bg-indigo-600 text-white' : 'bg-gray-200 hover:bg-gray-300'}">
                        ${i}
                    </a>
                </c:forEach>
            </div>
        </c:if>
    </div>
</body>
</html>