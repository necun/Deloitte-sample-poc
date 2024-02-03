package com.example.cameraxapp

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.cameraxapp.databinding.ActivityEmailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.TimeUnit

import okhttp3.logging.HttpLoggingInterceptor


class EmailActivity : AppCompatActivity() {
//    curl --location 'http://13.200.238.163:5001/send-email' \
//    --form 'to_email="snehal.trapsiya@gmail.com"' \
//    --form 'subject="This is test"' \
//    --form 'message="Hello please check you image"' \
//    --form 'image=@"/C:/Users/Administrator/Downloads/Necun/DocumentScanner/Sample_Images/2.jpeg"'

    private lateinit var viewBinding: ActivityEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initImageDisplay()
        viewBinding.emailImageView.setOnClickListener{
            initSendEmail()
        }

    }

    @SuppressLint("SuspiciousIndentation")
    private fun initSendEmail(){

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY


        val output_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/CameraX-Image-Output/"
        val file= File(output_path,"enhanced_image.jpg")
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("image",file.name,requestBody)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder().baseUrl("http://13.200.238.163:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(api::class.java)


//            val response =  retrofit.sendEmail(part,"agarwalkomal2030@gmail.com","filtered image","please the image in your mail")
//                   Log.d("response retrofit",response)
            val listCall: Call<UploadResponse> = retrofit.sendEmail(part,"agarwalkomal2030@gmail.com","this is test","hello please check your image")

            listCall.enqueue(object : Callback<UploadResponse> {
                override fun onResponse(
                    call: Call<UploadResponse>,
                    response: Response<UploadResponse>
                ) {

                    try {
                        val response = response.body()
                        if (response != null) {
                            Toast.makeText(
                                this@EmailActivity,
                                "" + response.message,
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                this@EmailActivity,
                                "please try again after sometime",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }catch(e:Exception){
                        Toast.makeText(
                            this@EmailActivity,
                            "please try again after sometime",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }


                override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                    Toast.makeText(this@EmailActivity,"please try again after sometime",Toast.LENGTH_LONG).show()
                }
            })



    }


    private fun initImageDisplay(){


        val output_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/CameraX-Image-Output/"

        val f= File(output_path,"enhanced_image.jpg")
        System.out.println("122334465=")
        val b= BitmapFactory.decodeStream(FileInputStream(f))
        viewBinding.imageView.setImageBitmap(b)

    }
}