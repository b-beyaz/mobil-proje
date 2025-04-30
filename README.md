Aşağıda verilen kodları skor listesini çağırmak istediğiniz aktiviteye koyun.

//Veritabanından verileri çekme işlemi

AppDatabase db = AppDatabase.getDatabase(this);        
       
ScoreDao scoreDao = db.scoreDao();

List<Score> scores = scoreDao.getAll();

//Verileri RecyclerView'a bağlama işlemi

RecyclerView recyclerView = findViewById(R.id.mRecyclerView);

Score_RecycleViewAdapter adapter = new Score_RecycleViewAdapter(this, scores);

recyclerView.setAdapter(adapter);

recyclerView.setLayoutManager(new LinearLayoutManager(this));

Bundan sonra skor listesinin gösterilmesini istediğiniz layout'a recyclerview nesnesini koyunuz. Örnek olarak:

  <androidx.recyclerview.widget.RecyclerView
  
  android:id="@+id/mRecyclerView"
  
android:layout_width="match_parent"

android:layout_height="0dp"

android:layout_marginTop="30dp"

android:layout_marginBottom="30dp"

android:layout_marginLeft="20dp"

android:layout_marginRight="20dp"

app:layout_constraintRight_toRightOf="parent"

app:layout_constraintLeft_toLeftOf="parent"

app:layout_constraintBottom_toBottomOf="parent"

/>

recycler_view.xml dosyasını, listenin daha farklı gözükmesini isterseniz ona göre değiştirebilirsiniz. (örnek olarak cardView)

Veritabanına verileri aşağıdaki şekilde ekleyebilirsiniz.

private void databaseTest() {
  
  AppDatabase db = AppDatabase.getDatabase(this);// Veritabanı nesnesini oluşturur
  
  ScoreDao scoreDao = db.scoreDao();// ScoreDao nesnesini oluşturur
  
  Score score = new Score("Player2", "Kolay", 200);// Yeni bir skor nesnesi oluşturur
  
  scoreDao.insertAll(score);// Veritabanına skor nesnesini ekler
  
  }

