package com.example.gonbe_house

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import com.example.gonbe_house.Post.PostActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class Login : AppCompatActivity() {

    private var mAuth:FirebaseAuth?=null

    private var database=FirebaseDatabase.getInstance()
    private var myRef=database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        mAuth= FirebaseAuth.getInstance()

        ivImagePerson.setOnClickListener( View.OnClickListener {
            checkPermission()
        })
    }

    fun LoginToFireBase(email:String,password:String){
        mAuth!!.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->

                if (task.isSuccessful){
                    Toast.makeText(applicationContext,"Successful login",Toast.LENGTH_LONG).show()
                    SaveImageInFirebase()
                }else {
                    Toast.makeText(applicationContext,"fail login",Toast.LENGTH_LONG).show()
                }
            }
    }

    fun SaveImageInFirebase(){
        var currentUser =mAuth!!.currentUser
        val email:String=currentUser!!.email.toString()
        val storage=FirebaseStorage.getInstance()
        val storgaRef=storage.getReferenceFromUrl("gs://gonbe-house.appspot.com")
        val df=SimpleDateFormat("ddMMyyHHmmss")
        val dataobj=Date()
        val imagePath= SplitString(email) + "."+ df.format(dataobj)+ ".jpg"
        val ImageRef=storgaRef.child("images/"+imagePath )
        ivImagePerson.isDrawingCacheEnabled=true
        ivImagePerson.buildDrawingCache()
        val drawable=ivImagePerson.drawable as BitmapDrawable
        val bitmap=drawable.bitmap
        val baos=ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data= baos.toByteArray()
        val uploadTask=ImageRef.putBytes(data)
        uploadTask.addOnFailureListener{
            Toast.makeText(applicationContext,"fail to upload",Toast.LENGTH_LONG).show()
        }.addOnSuccessListener { taskSnapshot ->
            var DownloadURL= taskSnapshot.storage.downloadUrl.toString()!!

            myRef.child("Users").child(currentUser.uid).child("email").setValue(currentUser.email)
            myRef.child("Users").child(currentUser.uid).child("ProfileImage").setValue(DownloadURL)
            LoadTweets()
        }
    }

    fun SplitString(email:String):String{
        val split= email.split("@")
        return split[0]
    }

    override fun onStart() {
        super.onStart()
        LoadTweets()
    }

    fun LoadTweets(){
        var currentUser =mAuth!!.currentUser

        if(currentUser!=null) {
//PostFragmentに変える時に必要？
//            val args = Bundle()
//            args.putString("email", currentUser.email)
//            args.putString("uid", currentUser.uid)
//            val fragment = post()
//            fragment.setArguments(args)

            var intent2 = Intent(this, MainActivity::class.java)
            var intent = Intent(this, PostActivity::class.java)
//            var intent3 = Intent(this, post::class.java)

            intent2.putExtra("email", currentUser.email)
            intent2.putExtra("uid", currentUser.uid)
            intent.putExtra("email", currentUser.email)
            intent.putExtra("uid", currentUser.uid)
//            intent3.putExtra("email", currentUser.email)
//            intent3.putExtra("uid", currentUser.uid)

            startActivity(intent2)
        }
    }

    val READIMAGE:Int=253
    fun checkPermission(){
        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf( android.Manifest.permission.READ_EXTERNAL_STORAGE),READIMAGE)
                return
            }
        }
        loadImage()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            READIMAGE->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    loadImage()
                }else{
                    Toast.makeText(applicationContext,"画像にアクセス出来ません",Toast.LENGTH_LONG).show()
                }
            }
            else-> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    val PICK_IMAGE_CODE=123
    fun loadImage(){

        var intent=Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==PICK_IMAGE_CODE  && data!=null && resultCode == RESULT_OK){
            val selectedImage=data.data
            val filePathColum= arrayOf(MediaStore.Images.Media.DATA)
            val cursor= contentResolver.query(selectedImage!!,filePathColum,null,null,null)
            cursor!!.moveToFirst()
            val coulomIndex=cursor!!.getColumnIndex(filePathColum[0])
            val picturePath=cursor!!.getString(coulomIndex)
            cursor!!.close()
            ivImagePerson.setImageBitmap(BitmapFactory.decodeFile(picturePath))
        }

    }

    fun buLogin(view:View){
        LoginToFireBase(etEmail.text.toString(),etEmail.text.toString())
    }
}