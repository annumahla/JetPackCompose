package com.example.composerapp.itemview

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.composerapp.model.Message

@Composable
fun ItemView(msg: Message) {
    Row(modifier = Modifier.padding(all = 20.dp)){
        msg.icon.let {
            Image(
                painter = painterResource(it),
                contentDescription = "launcher icon",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
        }

        Spacer(modifier = Modifier.width( 10.dp))

        var isExpanded by remember { mutableStateOf(false) }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )


        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }){
            Text(
                text = "${msg.name}!",
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Surface (
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ){
                Text(
                    text = "${msg.topic}.",
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }

    }
}