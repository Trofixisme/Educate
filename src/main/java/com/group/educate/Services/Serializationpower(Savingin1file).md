Great question — **YES, you can save the extra fields of Student, Recruiter, Admin with ZERO extra code**, because you are already using:


### ✔ **Java Serialization (`implements Serializable`)**


This automatically saves **all fields** from:


* the parent class (User)

* the subclass (Student / Recruiter / Admin)

* and nested objects


So you **don't** need a special student saving logic.


Let me explain it clearly.


---


# ✅ 1. Why the extra Student fields are saved automatically


If your Student class is:


```java

public class Student extends User implements Serializable {

    private int graduationYear;

    private String university;

    private String faculty;

}

```


And you do:


```java

Student s = new Student("Sandra", "Remon", "s@gmail.com", "123", 2025, "MIT", "Engineering");


repo.saveAll(List.of(s));

```


Then:


### ✔ All of this gets saved into users.txt:


* fname

* lname

* email

* password

* userID

* role

* graduationYear

* university

* faculty


Because **the entire object graph is serialized**.


You do NOT need extra code.


---


# ✔ 2. Your BaseRepository + FileManager already supports this


Your BaseRepository calls:


```java

FileManager.write(fileName, objects);

```


And FileManager writes:


```java

objectStream.writeObject(new ArrayList<>(List.of(objects)));

```


So the entire **Student object including its subclass fields** is written.


---


# ✔ 3. Reading also restores the subclass fields correctly


When you read using:


```java

repo.findAll();

```


FileManager does:


```java

(ArrayList<T>) objectStream.readObject();

```


And Java automatically reconstructs the correct subclass:


### If the stored object is Student → returned as Student


### If the stored object is Recruiter → returned as Recruiter


You don’t need custom parsing.


---


# ⭐ 4. Updated **UserService** with full saving logic


Here is a clean service:


```java

@Service

public class UserService {


    private static final String fileName = "data/users.txt";


    private final BaseRepository<User> repo =

            new BaseRepository<>(User.class, fileName);


    // Register user (works for Student, Recruiter, Admin)

    public void register(User user) throws Exception {

        ArrayList<User> users = repo.findAll();


        boolean exists = users.stream()

                .anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()));


        if (exists) {

            throw new Exception("User already exists");

        }


        users.add(user);

        repo.saveAll(users);

    }


    public User login(String email, String plainPassword) throws Exception {

        return repo.findAll().stream()

                .filter(u -> u.getEmail().equalsIgnoreCase(email))

                .filter(u -> PasswordHasher.matches(plainPassword, u.getHashedPassword()))

                .findFirst()

                .orElse(null);

    }


    public List<Student> findAllStudents() throws Exception {

        return repo.findAll().stream()

                .filter(u -> u.getRole() == UserRole.STUDENT)

                .map(u -> (Student) u)

                .toList();

    }


    public List<Recruiter> findAllRecruiters() throws Exception {

        return repo.findAll().stream()

                .filter(u -> u.getRole() == UserRole.RECRUITER)

                .map(u -> (Recruiter) u)

                .toList();

    }

}

```


---


# ⭐ 5. What happens when saving a Student?


Example:


```java

Student s = new Student("Sandra", "Remon", "s@gmail.com", "123", 2025, "MIT", "CS");


userService.register(s);

```


This will write something like:


```

[

   Student {

      fname="Sandra"

      lname="Remon"

      email="s@gmail.com"

      hashedPassword="..."

      userID=UUID

      role=STUDENT


      graduationYear=2025

      university="MIT"

      major="CS"

   }

]

```


Everything is preserved.


---


# ⭐ 6. Why does this work so well?


Because:


### Java Serialization stores:


* all primitive fields

* all Strings

* all nested objects

* all fields in parent and child classes


It's a complete snapshot.


---


# 💡 7. The service does NOT need to know subclasses


### Because filtering is based on:


```java

u.getRole()

```


So your UserService stays simple.


---


# 🔥 Summary


### ✔ Extra fields of Student ARE saved automatically


### ✔ No extra repo or parsing needed


### ✔ UserService simply stores a `User` list


### ✔ Java serialization handles all subclass data


### ✔ You can filter students using `role == STUDENT`


---


If you want, I can generate:


✅ StudentService if you really want separation

✅ Admin-only actions (delete users, promote to recruiter)

✅ Recruiter actions (post jobs, read applications)

✅ Best project folder structure


Just tell me!
// Json and APIs

