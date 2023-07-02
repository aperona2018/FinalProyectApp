# Proyecto final de Asignatura: PostIt!

### **Asignatura:** Laboratorio de Sistemas Móviles y Ubicuos
### **Alumno:** Antonio Perona Martínez ([aperona2018](https://github.com/aperona2018))

## Breve descripción del proyecto: PostIt!

PostIt! es una aplicación móvil la cual combina las características de un asistente de *workflow* mediante un sistema de creación de tareas, como de notas escritas o fechas importantes mediante su funcionalidad principal, la creación de *posts* privados con una dinámica sencilla para el usuario, recordando a los Post It empleados físicamente en la vida real para tomar notas, recordar fechas o apuntarse tareas que el usuario quiera mantener o recordar.


## Especificaciones del proyecto

**-Lenguaje de programación:** Kotlin

**- SDK seleccionado::** Oreo

**- Indentación:** 4 espacios

**- Versión xml:** 1.0

**- Encoding:** utf-8

**- Soporte de idiomas:** Inglés y español

## Funcionalidad del proyecto

La funcionalidad de esta aplicación comienza en el Main Activity, desde donde se van alternando las diferentes actividades o fragmentos de esta, según sea la funcionalidad requerida.

### Main Activity:

#### Tecnología utilizada (layout xml):

En cuanto al layout, cabe destacar:

**- androidx.appcompat.widget.Toolbar** 

**- com.google.android.material.navigation.NavigationView**

#### Tecnología utilizada (kotlin):

En cuanto al código, cabe destacar:

**- ViewModel**

**- drawer_layout**

**- ActionBarDrawer**


#### Explicación del código:

Lo más característico de la Main Activity es su ActionBarDrawer, la cual podremos accionar haciendo click en la parte superior izquierda de la aplicación, esta nos abrirá las opciones de ir a las partes principal, "sobre nosotros", login y exit de la aplicación.

Asimismo, el fragmento principal que se mostrará en el Main Activity será el Home Fragment gracias a este código:

```kotlin

if (savedInstanceState == null){
    supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container, HomeFragment()).commit()
    navigationView.setCheckedItem(R.id.nav_home)
}

```

Cabe destacar la instanciación del objeto navigationView y el override de su función **onNavigationItemSelected**:

```kotlin
val navigationView = findViewById<NavigationView>(R.id.nav_view)
navigationView.setNavigationItemSelectedListener(this)
```

```kotlin
override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> supportFragmentManager.beginTransaction().apply{
                    replace(R.id.fragment_container, HomeFragment())
                    addToBackStack(null)
                    commit()
                }
            R.id.nav_about -> supportFragmentManager.beginTransaction().apply{
                replace(R.id.fragment_container, AboutFragment())
                addToBackStack(null)
                commit()
            }
            R.id.nav_login -> supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, LoginFragment())
                addToBackStack(null)
                commit()
            }

            R.id.nav_exit -> {
                Toast.makeText(this, R.string.ExitToast, Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
```

En este último snippet de código comprobamos el itemId de las distintas opciones expuestas en el NavigationView y, dependiendo de la id del diferente item, transiccionaremos al fragment pertinente o saldremos de la aplicación.


### Home Fragment:

#### Tecnología utilizada (layout xml):

En cuanto al layout, cabe destacar:

**- androidx.appcompat.widget.SearchView** 

**- androidx.recyclerview.widget.RecyclerView**

#### Tecnología utilizada (kotlin):

En cuanto al código, cabe destacar:

**- ViewModel**

**- RecyclerView**

**- SearchView**

**- AdapterClass**


#### Explicación del código:

El Home Fragment se caracteriza por mostrar mediante RecyclerViews una lista de posibles opciones a realizar en esta parte de la aplicación, entre las cuales se encuentran:

- Creación de tareas

- Creación de fechas

- Creación de notas

- Puntuación de la aplicación

Asimismo, disponemos de una SearchView para buscar la actividad que el usuario desee realizar.


En cuanto al principio del fragment, utilizamos el ViewModel (UserViewModel) creado para extraer su información, la cual va a ser el nombre de usuario para colocarlo en el header de la barra de navegación

```kotlin
username = userViewModel.getUsername()
val navigationView = activity?.findViewById<NavigationView>(R.id.nav_view)
val header = navigationView?.getHeaderView(0)
var headerUsername = header?.findViewById<TextView>(R.id.header_user)
if (headerUsername != null) {
    headerUsername.text = userViewModel.getUsername()
}
```

Para la funcionalidad del SearchView, definimos su función **setOnQueryTextListener**, haciendo un override tanto para si hemos realizado una búsqueda como si hemos cambiado el texto de la query

```kotlin
searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    dataList.forEach{
                        if (it.dataTitle.toLowerCase(Locale.getDefault()).contains(searchText)){
                            searchList.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()
                } else{
                    searchList.clear()
                    searchList.addAll(dataList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })
```

Además, en cuanto al RecyclerView, empleamos una AdapterClass para, dado un ArrayList de datos, mostrar dichos items de forma vertical.


```kotlin
class AdapterClass(private val dataList : ArrayList<DataClass>): RecyclerView.Adapter<AdapterClass.ViewHolderClass>() {

    var onItemClick: ((DataClass) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
        holder.rvImage.setImageResource(currentItem.dataImage)
        holder.rvTitle.text = currentItem.dataTitle

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvImage : ImageView = itemView.findViewById(R.id.image)
        val rvTitle : TextView = itemView.findViewById(R.id.title)
    }
}
```

(El layout de cada item será un **CardView** con una imagen y un título)

```xml
<androidx.cardview.widget.CardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_marginHorizontal="10dp"
    android:layout_marginVertical="10dp"
    app:cardCornerRadius="20dp"
    app:cardElevation="8dp">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <ImageView
            android:layout_width="60dp"
            android:layout_height="60dp"
            android:id="@+id/image"
            android:src="@drawable/ic_list"
            android:layout_marginStart="20dp"
            android:padding="8dp"
            android:adjustViewBounds="true"
            android:scaleType="fitXY"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintTop_toTopOf="parent"/>
        
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/title"
            android:text="@string/item_title"
            android:textSize="16sp"
            android:textColor="@color/black"
            android:layout_marginStart="20dp"
            app:layout_constraintStart_toEndOf="@id/image"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"/>


    </androidx.constraintlayout.widget.ConstraintLayout>

</androidx.cardview.widget.CardView>
```

De esta forma, gracias al adapter, podremos mostrar los items del recyclerView y gestionar los eventos de click a cada item:

```kotlin
myAdapter = AdapterClass(searchList)
        recyclerView.adapter = myAdapter

        myAdapter.onItemClick = {
            when(it.dataTitle){
                getString(R.string.chores) -> {
                    val choresIntent = Intent(activity, ChoresActivity::class.java)
                    choresIntent.putExtra("username", userViewModel.getUsername())
                    activity?.startActivity(choresIntent)
                }
                getString(R.string.dates) -> {
                    val dateIntent = Intent(activity, DateActivity::class.java)
                    dateIntent.putExtra("username", userViewModel.getUsername())
                    activity?.startActivity(dateIntent)
                }
                getString(R.string.write) -> {
                    val notesIntent = Intent(activity, NotesActivity::class.java)
                    notesIntent.putExtra("username", userViewModel.getUsername())
                    activity?.startActivity(notesIntent)
                }
                getString(R.string.rating) -> {
                    val ratingsIntent = Intent(activity, RatingActivity::class.java)
                    ratingsIntent.putExtra("username", userViewModel.getUsername())
                    activity?.startActivity(ratingsIntent)
                }
            }
        }
```


### About Fragment:

#### Tecnología utilizada (layout xml):

En cuanto al layout, cabe destacar:

**- androidx.cardview.widget.CardView** 

#### Tecnología utilizada (kotlin):


#### Explicación del código:

Muestra la información relevante del creador del proyecto:

- Nombre

- Grado

- Github

### Login Fragment:

#### Tecnología utilizada (layout xml):

En cuanto al layout, cabe destacar:

**- androidx.cardview.widget.CardView** 

#### Tecnología utilizada (kotlin):


**- ViewModel**

**- Firebase database**

#### Explicación del código:

En este fragment se nos presenta la posibilidad de iniciar sesión en la aplicación. Mediante la introducción del usuario y su pertinente contraseña.

Si los campos usuario y contraseña no están vacíos, se comprueba en la base de datos en la nube si existe un usuario con su pertinente contraseña. Si es así, se vuelve al fragment Home pero con los permisos de usuario y con su pertinente nombre de usuario guardados en el ViewModel para su posterior extracción.

```kotlin
if ((username.isNotEmpty()) && (password.isNotEmpty())){
                val dbRef = FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")

                dbRef.get().addOnCompleteListener{
                    if (it.isSuccessful) {
                        val result = it.result.children.mapNotNull { doc ->
                            doc.getValue(UserClass::class.java)
                        }
                        for (user in result){
                            if ((user.userName == username) && (user.userPassword == password)){
                                userViewModel.setUsername(user.userName.toString())
                                Toast.makeText(activity, R.string.toast_logged, Toast.LENGTH_SHORT).show()
                                activity?.supportFragmentManager?.beginTransaction()?.apply{
                                    replace(R.id.fragment_container, HomeFragment())
                                    addToBackStack(null)
                                    commit()
                                }
                            }
                        }
                    }
                }
            }
```

Además, para los usuarios no registrados, se le da la opción de registrarse.


### Register Fragment:

#### Tecnología utilizada (layout xml):

En cuanto al layout, cabe destacar:

**- androidx.cardview.widget.CardView** 

#### Tecnología utilizada (kotlin):


**- ViewModel**

**- Firebase database**

#### Explicación del código:

En este fragment se pedirán los campos pertinentes para la creación de un nuevo usuario:

- nombre de usuario

- email

- número de teléfono

- contraseña

Y si todos los campos han sido rellenados, se creará una instancia de UserClass, la cual será almacenada en la base de datos de firebase


```kotlin
if (username.isNotEmpty() && email.isNotEmpty() && number.isNotEmpty() && password.isNotEmpty()){
                val userClass = UserClass(username, email, number, password)

                FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users").child(username)
                    .setValue(userClass).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(activity, R.string.toast_registered, Toast.LENGTH_SHORT).show()
                            userViewModel.setUsername(username.toString())
                            activity?.supportFragmentManager?.beginTransaction()?.apply{
                                replace(R.id.fragment_container, HomeFragment())
                                addToBackStack(null)
                                commit()
                            }
                        }
                    }.addOnFailureListener { e ->
                        Toast.makeText(activity, e.message.toString(), Toast.LENGTH_SHORT).show()
                    }
            } else{
                Toast.makeText(activity, R.string.toast_fields_filled, Toast.LENGTH_SHORT).show()
            }
``` 

La definición de la clase UserClass: 

```kotlin
class UserClass {
    var userName : String? = null
    var userEmail : String? = null
    var userPhone : String? = null
    var userPassword : String? = null

    constructor(userName: String?, userEmail: String?, userPhone: String?, userPassword: String?){
        this.userName = userName
        this.userEmail = userEmail
        this.userPhone = userPhone
        this.userPassword = userPassword
    }

    constructor()
    
}
```

### Chores Activity:

#### Tecnología utilizada (layout xml):

En cuanto al layout, cabe destacar:

**- androidx.recyclerview.widget.RecyclerView** 

**- com.google.android.material.floatingactionbutton.FloatingActionButton**

#### Tecnología utilizada (kotlin):


**- Bundle**

**- Firebase database**

**- Binding**

#### Explicación del código:


En esta actividad, se nos mostrarán mediante RecyclerView todas las tareas almacenadas en la base de datos que ha creado nuestro usuario. Con un formato de tarea que contendrá:

- Título

- Descripción

- Prioridad

Asimismo, mediante un floating action button, podremos crear una nueva tarea a añadir


En esta actividad, utilizamos la opción de binding para las vistas

```kotlin
binding = ActivityChoresBinding.inflate(layoutInflater)
setContentView(binding.root)
```

Y los bundles para el paso de datos entre fragments. En este caso se utiliza para obtener el nombre del usuario

```kotlin
val bundle : Bundle? = this.intent.extras
val username : String? = bundle?.getString("username")
```

Después, se busca en la base de datos todos las tareas creadas por dicho usuario y las muestra

```kotlin
databaseReference = FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Chores").child(username.toString())
        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                choreList.clear()
                for (itemSnapshot in snapshot.children){
                    val choreClass = itemSnapshot.getValue(ChoreClass::class.java)
                    if (choreClass != null){
                        choreList.add(choreClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })
```


### Upload Activity:

#### Tecnología utilizada (layout xml):

En cuanto al layout, cabe destacar:

**- androidx.cardview.widget.CardView** 


#### Tecnología utilizada (kotlin):

**- Bundle**

**- Firebase database**

**- Binding**

#### Explicación del código:


En esta actividad se pide al usuario introducir la información pertinente a la tarea que quiere crear:

- Nombre

- Descripción

- Prioridad


Asimismo, se crea una instancia de la nota con la información introducida y se almacena en la base de datos.


La clase del objeto nota:
```kotlin
class NoteClass {
    var noteTitle: String? = null
    var noteContent : String? = null
    var noteCreator: String? = null

    constructor(noteTitle: String?, noteContent: String?){
        this.noteTitle = noteTitle
        this.noteContent = noteContent
    }

    constructor()
}
```

### Dates Activity:

#### Tecnología utilizada (layout xml):

En cuanto al layout, cabe destacar:

**- androidx.recyclerview.widget.RecyclerView** 

**- com.google.android.material.floatingactionbutton.FloatingActionButton**

#### Tecnología utilizada (kotlin):


**- Bundle**

**- Firebase database**

**- Binding**

#### Explicación del código:


En esta actividad, se nos mostrarán mediante RecyclerView todas las fechas almacenadas en la base de datos que ha creado nuestro usuario. Con un formato de tarea que contendrá:

- Título

- Fecha

- Borrar

Asimismo, mediante un floating action button, podremos crear una nueva fecha a añadir


En esta actividad, utilizamos la opción de binding para las vistas y los bundles para el paso de datos entre fragments. En este caso se utiliza para obtener el nombre del usuario. Después, se busca en la base de datos todos las fechas creadas por dicho usuario y las muestra

Asimismo, se puede borrar la fecha pulsando en el icono de la papelera mediante la función onBindViewHolder del DateAdapter:

```kotlin
override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.recDateTitle.text = dateList[position].dateTitle
        holder.recDateText.text = dateList[position].dateText
        val creator : String = dateList[position].creator.toString()
        holder.recDelete.setOnClickListener {
            val title : String? = dateList[position].dateTitle
            if (title != null) {
                FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Dates").child(creator)
                    .child(title).removeValue().addOnSuccessListener {
                        Toast.makeText(context, R.string.toast_date_deleted, Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
```


### Upload Date Activity:

#### Tecnología utilizada (layout xml):

En cuanto al layout, cabe destacar:

**- androidx.cardview.widget.CardView** 


#### Tecnología utilizada (kotlin):

**- Bundle**

**- Firebase database**

**- Binding**

#### Explicación del código:


En esta actividad se pide al usuario introducir la información pertinente a la fecha que quiere crear:

- Nombre de fecha

- fecha (haciendo click en el icono de la fecha)

Asimismo, se crea una instancia de la fecha con la información introducida y se almacena en la base de datos.


La clase del objeto fecha:
```kotlin
class DateClass {
    var dateTitle : String? = null
    var dateText : String? = null
    var creator : String? = null

    constructor(dateTitle: String?, dateText: String?, creator: String?){
        this.dateTitle = dateTitle
        this.dateText = dateText
        this.creator = creator
    }

    constructor()
}
```


### Notes Activity:

#### Tecnología utilizada (layout xml):

En cuanto al layout, cabe destacar:

**- androidx.recyclerview.widget.RecyclerView** 

**- com.google.android.material.floatingactionbutton.FloatingActionButton**

#### Tecnología utilizada (kotlin):


**- Bundle**

**- Firebase database**

**- Binding**

#### Explicación del código:


En esta actividad, se nos mostrarán mediante RecyclerView todas las notas almacenadas en la base de datos que ha creado nuestro usuario. Con un formato de tarea que contendrá:

- Título

- Borrar

- Editar

Asimismo, mediante un floating action button, podremos crear una nueva nota a añadir


En esta actividad, utilizamos la opción de binding para las vistas y los bundles para el paso de datos entre fragments. En este caso se utiliza para obtener el nombre del usuario. Después, se busca en la base de datos todos las notas creadas por dicho usuario y las muestra

Asimismo, se puede borrar la nota pulsando en el icono de la papelera mediante la función onBindViewHolder del NoteAdapter:

```kotlin
override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.recNoteTitle.text = noteList[position].noteTitle
        val username = noteList[position].noteCreator
        holder.recDelete.setOnClickListener {
            val title : String? = noteList[position].noteTitle
            if (title != null) {
                FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Notes").child(username.toString())
                    .child(title).removeValue().addOnSuccessListener {
                        Toast.makeText(context, R.string.toast_note_deleted, Toast.LENGTH_SHORT).show()
                    }
            }
        }

        holder.recChange.setOnClickListener {
            val changeIntent = Intent(this.context, ChangeNoteActivity::class.java)
            changeIntent.putExtra("title", holder.recNoteTitle.text)
            changeIntent.putExtra("username", username)
            this.context?.startActivity(changeIntent)
        }
    }
```

### Upload Note Activity:

#### Tecnología utilizada (layout xml):

En cuanto al layout, cabe destacar:

**- androidx.cardview.widget.CardView** 


#### Tecnología utilizada (kotlin):

**- Bundle**

**- Firebase database**

**- Binding**

#### Explicación del código:


En esta actividad se pide al usuario introducir la información pertinente a la nota que quiere crear:

- Nombre de nota

- Contenido

Asimismo, se crea una instancia de la nota con la información introducida y se almacena en la base de datos.


La clase del objeto nota:
```kotlin
class NoteClass {
    var noteTitle: String? = null
    var noteContent : String? = null
    var noteCreator: String? = null

    constructor(noteTitle: String?, noteContent: String?){
        this.noteTitle = noteTitle
        this.noteContent = noteContent
    }

    constructor()
}
```

### Change Note Activity:

#### Tecnología utilizada (layout xml):

En cuanto al layout, cabe destacar:

**- androidx.cardview.widget.CardView** 


#### Tecnología utilizada (kotlin):

**- Firebase database**

**- Binding**

#### Explicación del código:


En esta actividad se pide al usuario introducir el contenido de la nota que quiere cambiar, obtenida mediante el siguiente código:

```kotlin
val title: String = intent.getStringExtra("title").toString()
        val username: String = intent.getStringExtra("username").toString()
        val dbRef = FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Notes").child(username)

        dbRef.get().addOnCompleteListener{
            if (it.isSuccessful) {
                val result = it.result.children.mapNotNull { doc ->
                    doc.getValue(NoteClass::class.java)
                }
                for (note in result){
                    if ((note.noteTitle == title)){
                        binding.actualNoteContent.text = note.noteContent
                    }
                }
            }
        }
```


Asimismo, se crea una instancia de la nota con la información cambiada y se almacena de nuevo en la base de datos.

```kotlin
binding.saveChangeNoteButton.setOnClickListener {
            Toast.makeText(this, R.string.toast_changes_saved, Toast.LENGTH_SHORT).show()
            val content : String? = binding.noteContentChange.text.toString()
            val noteClass = NoteClass(title, content)
            FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Notes").child(username).child(title)
                .setValue(noteClass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@ChangeNoteActivity, R.string.toast_saved, Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this@ChangeNoteActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }
```


### Rating Activity:

#### Tecnología utilizada (layout xml):

En cuanto al layout, cabe destacar:

**- RatingBar** 


#### Tecnología utilizada (kotlin):

**- Bundle**

**- Firebase database**

**- Binding**

#### Explicación del código:

En esta actividad, el usuario podrá valorar la aplicación mediante la puntuación de la rating bar.

De esta forma, se podrá hacer un seguimiento de la valoración de los usuarios sobre el servicio provisto por la aplicación desarrollada.

La dinámica de tratamiento de datos y almacenamiento mediante código es:

```kotlin
binding.ratingButton.setOnClickListener {
            if (username != "") {
                if (binding.ratingBar != null) {
                    val rating = binding.ratingBar.rating

                    val builder = AlertDialog.Builder(this@RatingActivity)
                    builder.setCancelable(false)
                    builder.setView(R.layout.progress_layout)
                    val dialog = builder.create()
                    dialog.show()
                    val datetime = LocalDateTime.now()
                    var dateStr = datetime.toString()
                    dateStr.replace(".".toRegex(), "")
                    print(dateStr)

                    FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app")
                        .getReference("Rating").child(username.toString())
                        .setValue(rating).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this@RatingActivity, "Saved", Toast.LENGTH_SHORT)
                                    .show()
                                finish()
                            }
                        }.addOnFailureListener { e ->
                            Toast.makeText(
                                this@RatingActivity, e.message.toString(), Toast.LENGTH_SHORT
                            ).show()
                        }

                } else {
                    Toast.makeText(this, R.string.toast_rating_bar, Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, R.string.toast_must_login, Toast.LENGTH_SHORT).show()
            }
        }
```



## Firebase database

Firebase es una plataforma digital de Google diseñada para el desarrollo de aplicaciones web y móviles, en este caso hemos utilizado su funcionalidad de base de datos: firebase database.

Firebase database es una base de datos de tipo NoSQL en la nube la cual facilita a los desarrolladores el alojamiento de información en la nube manteniendo unos bajos costes para esta. Para ello, se almacenan los datos en un bucket de Google Cloud Storage y los hace accesibles a través de Firebase. Asimismo, nos proporciona una buena escalabilidad en etapas tempranas de la aplicación. Puesto que nos permite almacenar hasta un total de 3000 usuarios. 


















  
