package com.example.ntnews.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.ntnews.ui.theme.Platinum
import com.example.ntnews.utils.formatDateTime

@Composable
fun FavoriteCardComponent(
    newsTitle :String,
    newsSection : String,
    newsDate:String,
    newsAuthor: String,
    imageUrl: String?,
    newsContent:String,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit
){

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .border(1.dp, Platinum, RoundedCornerShape(15.dp)),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                    .background(Color.Transparent)
            ) {
                if (imageUrl != null) {
                    Image(
                        painter = rememberImagePainter(imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.4f))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = newsSection.uppercase(),
                            fontSize = 14.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(Color.Red, shape = RoundedCornerShape(4.dp))
                                .padding(horizontal = 4.dp, vertical = 2.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = newsTitle,
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier=Modifier.height(9.dp))

            Row( modifier=Modifier
                .fillMaxWidth()
                .padding(top=8.dp,bottom=8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column ( modifier=Modifier.weight(7f),
                    verticalArrangement = Arrangement.Center
                )
                {
                    Text(
                        text= formatDateTime(newsDate),
                        fontSize = 14.sp,
                        color=Color.Gray
                    )

                    Spacer(modifier=Modifier.height(5.dp))

                    Text(
                        text=newsAuthor,
                        fontSize = 14.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        color=Color.Gray
                    )
                }
                IconButton(
                    onClick=onRemoveClick,
                    modifier=Modifier
                        .weight(1f)
                        .padding(7.dp)
                        .background(Color.LightGray.copy(0.5f), shape = RoundedCornerShape(50))
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Remove from favorites",
                        tint=Color.Red,
                        modifier=Modifier.size(15.dp)
                    )
                }
            }

            Spacer(modifier=Modifier.height(8.dp))

            Text(
                text=newsContent,
                fontSize = 15.sp,
                color=Color.Black,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}