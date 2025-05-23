package com.example.ntnews.ui.components

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.ntnews.ui.theme.SoftBlue

@Composable
fun CategoryCardComponent(
    newsTitle:String,
    newsContent:String,
    newsSection:String,
    newsDate:String,
    newsAuthor:String,
    imageUrl: String?,
    onClick: () -> Unit,
){
    Card(modifier=Modifier
        .padding(9.dp)
        .fillMaxWidth()
        .border(1.dp, Color.Gray, RoundedCornerShape(15.dp)),
        shape = RoundedCornerShape(15.dp),
        colors=CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column (modifier=Modifier.padding(13.dp))
        {
             Box(modifier=Modifier
                 .fillMaxWidth()
                 .height(280.dp)
                 .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                 .background(Color.Transparent)
             ){
                 if (imageUrl != null){
                     Image(
                         painter = rememberImagePainter(imageUrl),
                         contentDescription = null,
                         modifier=Modifier
                             .fillMaxSize()
                             .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
                         contentScale = ContentScale.Crop
                     )
                     Column (
                         modifier=Modifier
                             .fillMaxSize()
                             .background(Color.Black.copy(alpha = 0.4f))
                             .padding(13.dp),
                         verticalArrangement = Arrangement.Bottom,
                         horizontalAlignment = Alignment.Start
                     ){
                        Text(
                            text=newsSection.uppercase(),
                            fontSize = 15.sp,
                            color=Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier=Modifier.background(Color.Red, shape=RoundedCornerShape(5.dp))
                                .padding(horizontal = 5.dp,vertical=3.dp)
                        )
                         Spacer(modifier=Modifier.height(5.dp))

                         Text(
                             text=newsTitle,
                             fontSize = 18.sp,
                             color=Color.White,
                             fontWeight = FontWeight.Bold,
                             maxLines=2,
                             overflow = TextOverflow.Ellipsis
                         )
                     }
                 }
             }
            Spacer(modifier=Modifier.height(5.dp))

            Row (
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(top=8.dp, bottom=8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(modifier=Modifier.weight(7f))
                {
                    Text(
                        text=newsDate,
                        fontSize=14.sp,
                        color=Color.Gray
                    )

                    Spacer(modifier=Modifier.height(5.dp))

                   Text(
                       text=newsAuthor,
                       fontSize=15.sp,
                       color=Color.Gray
                   )
                }
            }
            Spacer(modifier=Modifier.height(8.dp))

            Text(
                text=newsContent,
                fontSize=16.sp,
                color=Color.Black,
                maxLines=3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier=Modifier.height(8.dp))

            Button(
                onClick=onClick,
                modifier=Modifier.fillMaxWidth(),
                colors=ButtonDefaults.buttonColors(containerColor = SoftBlue)
            ) {
                Text(text="Read More", color=Color.White)
            }
        }
    }
}