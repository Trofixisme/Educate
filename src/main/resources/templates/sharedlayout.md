# How to Create a Shared Layout (Header & Footer) in Thymeleaf

Great question! Here are the best ways to create a shared footer layout in Spring Boot with Thymeleaf:

---

## Method 1: Thymeleaf Fragments (Recommended)

### Step 1: Create a fragments file

**File location:** `src/main/resources/templates/fragments/layout.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:fragment="head(title)">
    <meta charset="UTF-8">
    <title th:text="${title}">InternMap</title>
    <link rel="icon" type="image/png" th:href="@{/images/Logo Glass.png}">
    <link rel="stylesheet" th:href="@{/InternMapGeneral.css}">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>

<!-- Header Fragment -->
<header th:fragment="header">
    <div class="header-top">
        <div class="header-brand">InternMap</div>
        <nav class="header-nav">
            <a th:href="@{/login}">
                <button class="btn-header transparent">Sign in</button>
            </a>
            <a th:href="@{/signup-choice}">
                <button class="btn-header primary">Sign up</button>
            </a>
            <a th:href="@{/profile}">
                <button class="btn-header transparent">
                    <img class="profile-icon" th:src="@{/images/person_fill.png}" alt="Profile">
                </button>
            </a>
        </nav>
    </div>
    <div class="header-hero">
        <img class="logo" th:src="@{/images/Logo Glass.png}" alt="InternMap Logo">
        <h1 class="hero-title">InternMap</h1>
        <p class="hero-subtitle">Welcome to the platform that sets your future for you</p>
    </div>
</header>

<!-- Footer Fragment -->
<footer th:fragment="footer">
    <div class="footer-content">
        <div class="footer-brand">InternMap</div>
        <div class="footer-links">
            <ul class="footer-nav">
                <li><a th:href="@{/}">Home</a></li>
                <li><a th:href="@{/JobPostings}">Job Postings</a></li>
                <li>RoadMaps</li>
                <li>About us</li>
            </ul>
        </div>
    </div>
</footer>

</body>
</html>
```

### Step 2: Use fragments in your pages

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:replace="~{fragments/layout :: head('Login - InternMap')}"></head>

<body>
    <!-- Include Header -->
    <div th:replace="~{fragments/layout :: header}"></div>
    
    <!-- Your Page Content -->
    <div class="form-container">
        <h2 class="form-title">Sign In</h2>
        <form th:action="@{/login}" th:object="${user}" method="post">
            <!-- form content -->
        </form>
    </div>
    
    <!-- Include Footer -->
    <div th:replace="~{fragments/layout :: footer}"></div>
</body>
</html>
```

---

## Method 2: Base Layout Template

### Step 1: Create base layout

**File location:** `src/main/resources/templates/layouts/base.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" 
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">
<head>
    <meta charset="UTF-8">
    <title layout:title-pattern="$CONTENT_TITLE - $LAYOUT_TITLE">InternMap</title>
    <link rel="stylesheet" th:href="@{/InternMapGeneral.css}">
    <!-- other head content -->
</head>

<body>
    <!-- Header -->
    <header th:replace="~{fragments/layout :: header}"></header>
    
    <!-- Main Content (this will be replaced by each page) -->
    <main layout:fragment="content">
        <!-- Page-specific content goes here -->
    </main>
    
    <!-- Footer -->
    <footer th:replace="~{fragments/layout :: footer}"></footer>
</body>
</html>
```

### Step 2: Extend the layout

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{layouts/base}">

<head>
    <title>Login</title>
</head>

<body>
    <main layout:fragment="content">
        <!-- Your page-specific content -->
        <div class="form-container">
            <h2 class="form-title">Sign In</h2>
            <!-- form content -->
        </div>
    </main>
</body>
</html>
```

---

## Method 3: Simple Include (Easiest)

### Step 1: Create separate files

**File location:** `src/main/resources/templates/common/header.html`

```html
<header xmlns:th="http://www.thymeleaf.org">
    <div class="header-top">
        <!-- header content -->
    </div>
</header>
```

**File location:** `src/main/resources/templates/common/footer.html`

```html
<footer xmlns:th="http://www.thymeleaf.org">
    <div class="footer-content">
        <!-- footer content -->
    </div>
</footer>
```

### Step 2: Include in your pages

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>InternMap - Login</title>
    <!-- head content -->
</head>

<body>
    <!-- Include Header -->
    <div th:insert="~{common/header}"></div>
    
    <!-- Your Page Content -->
    <div class="form-container">
        <!-- content -->
    </div>
    
    <!-- Include Footer -->
    <div th:insert="~{common/footer}"></div>
</body>
</html>
```

---

## Comparison

| Method | Best For | Pros | Cons |
|--------|----------|------|------|
| **Fragments** | Most projects | Flexible, organized, reusable | Need to understand fragments |
| **Layout Decorator** | Large projects | DRY, powerful | Requires dependency, more complex |
| **Simple Include** | Small projects | Very simple, easy to understand | Less flexible |

---

## Recommendation

**I recommend Method 1 (Fragments)** - it's the most balanced approach and works great with Spring Boot!

### Key Thymeleaf Syntax

- `th:fragment="name"` - Defines a reusable fragment
- `th:replace="~{path :: fragmentName}"` - Replaces the element with the fragment
- `th:insert="~{path :: fragmentName}"` - Inserts the fragment inside the element
- `th:include="~{path}"` - Includes the entire file

### Tips

1. **Use `th:replace`** for headers and footers (replaces the wrapper div)
2. **Use `th:insert`** when you want to keep the wrapper element
3. **Organize fragments** in a `fragments/` folder for better structure
4. **Pass parameters** to fragments using syntax like `head('Page Title')`

---

## Example Project Structure

```
src/main/resources/
└── templates/
    ├── fragments/
    │   └── layout.html          (header, footer, head fragments)
    ├── common/
    │   ├── header.html          (alternative approach)
    │   └── footer.html          (alternative approach)
    ├── layouts/
    │   └── base.html            (base layout template)
    ├── login.html               (uses fragments)
    ├── register.html            (uses fragments)
    └── home.html                (uses fragments)
```