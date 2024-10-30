# Système de Gestion de Bibliothèque

## Table des Matières
1. [Introduction](#introduction)
2. [Fonctionnalités](#fonctionnalités)
3. [Architecture](#architecture)
5. [Utilisation](#utilisation)
6. [Détails Techniques](#détails-techniques)

## Introduction
Le système de gestion de bibliothèque est une application Java conçue pour faciliter la gestion des utilisateurs et des livres dans une bibliothèque. Il permet aux utilisateurs de louer, retourner et afficher des livres tout en gérant les informations des utilisateurs dans un format JSON.

## Fonctionnalités
- **Gestion des utilisateurs :**
  - Authentification des utilisateurs.
  - Création de nouveaux utilisateurs.
  - Chargement des utilisateurs depuis un fichier JSON.

- **Gestion des livres :**
  - Chargement des livres depuis un fichier JSON.
  - Affichage des livres disponibles.
  - Consultation des détails d'un livre.
  - Location et retour de livres.
  - Exportation du catalogue des livres.

- **Services de location :**
  - Vérification des limites de location pour les utilisateurs.
  - Gestion des livres déjà loués.

## Architecture
Le système est construit selon une architecture orientée services, avec les composants principaux suivants :

- **Classes principales :**
  - `MenuService` : Gère les interactions avec l'utilisateur via le menu.
  - `UserManager` : Gère les utilisateurs, y compris le chargement et la sauvegarde des données.
  - `LibraryService` : Fournit des services relatifs aux livres.
  - `RentalService` : Gère les opérations de location et de retour des livres.
  - `User` : Représente un utilisateur.
  - `Book` : Représente un livre dans la bibliothèque.

- **Gestion des données :**
  - Les données des utilisateurs et des livres sont stockées au format JSON, permettant une manipulation facile et une persistance des données.

## Utilisation

Après avoir exécuté l'application, suivez les étapes ci-dessous pour naviguer et utiliser les fonctionnalités :

1. **Connexion à l'application :**
   - Lorsque l'application démarre, elle vous invite à entrer votre nom d'utilisateur.
   - Si vous êtes un nouvel utilisateur, vous pouvez choisir de créer un compte en répondant aux invites.
   - Pour quitter, tapez simplement `exit`.

2. **Menu Principal :**
   - Après la connexion, vous serez présenté avec un menu principal contenant les options suivantes :
     - **Louer un livre :** Permet à l'utilisateur de sélectionner un livre disponible pour le louer.
     - **Afficher les livres disponibles :** Affiche une liste de tous les livres actuellement disponibles dans la bibliothèque.
     - **Voir les détails d'un livre :** Permet de consulter des informations détaillées sur un livre spécifique (titre, auteur, ISBN, etc.).
     - **Rendre un livre :** Vous permet de retourner un livre que vous avez loué.
     - **Exporter le catalogue :** Génère un fichier contenant tous les livres de la bibliothèque au format JSON.
     - **Créer un livre :** Permet aux utilisateurs autorisés de créer un nouveau livre et de l'ajouter à la bibliothèque.
     - **Quitter l'application :** Ferme l'application.

3. **Interactions avec le système :**
   - Suivez les instructions affichées à l'écran pour chaque option du menu.
   - Utilisez les commandes appropriées pour naviguer dans le système. Les réponses sont en général saisies au clavier, et les confirmations sont souvent requises pour certaines actions (comme la location ou le retour d'un livre).

## Détails Techniques

Le projet est construit en Java et utilise plusieurs concepts clés de la programmation orientée objet. Voici un aperçu des composants et des technologies utilisés :

- **Langage de programmation :** Java 11 ou supérieur.
- **Bibliothèques utilisées :**
  - **Gson** : Utilisé pour la sérialisation et la désérialisation des données au format JSON.
- **Structure du projet :**
  - Les classes principales incluent :
    - `MenuService` : Gère les interactions utilisateur et le menu principal.
    - `UserManager` : Gère la création, la recherche et la sauvegarde des utilisateurs.
    - `LibraryService` : Gère les livres, y compris leur location et leur retour.
    - `RentalService` : Logique spécifique à la location de livres.
    - `User` et `Book` : Représentent respectivement les utilisateurs et les livres.

### Exemples de données

- **Exemple de fichier `users.json` :**
    ```json
    [
        {
          "GUID": "a7f16cb1-4d3d-45b6-a5e5-9d10cfa24b35",
          "name": "Alice Smith",
          "rentedBooks": []
        }
    ]
    ```

- **Exemple de fichier `book_catalog.json` :**
    ```json
    [
        {
    "GUID": "e9b5e6e6-d888-4f0a-9a0c-4f276e725c7e",
    "title": "Java Network Programming",
    "description": "A guide to network programming in Java.",
    "author": "Elliotte Rusty Harold",
    "price": 54.99,
    "isRented": false
  },
    ]
    ```

