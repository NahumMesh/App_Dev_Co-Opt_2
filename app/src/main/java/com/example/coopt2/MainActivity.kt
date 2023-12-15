@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.coopt2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coopt2.ui.theme.Coopt2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Coopt2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Run the scaffold demo function.
                    ScaffoldDemo()
                }
            }
        }
    }
}

// Data class to hold all our variables that relate to the navigation item state.
data class NavItemState(
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val hasBadge : Boolean,
    val badgeNum : Int
)

// Scaffold demo function with a top and bottom bar.
@Composable
fun ScaffoldDemo(modifier: Modifier = Modifier) {
    // Create a list of nav items and define their attributes.
    val items = listOf(
        // Home nav bar item.
        NavItemState(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasBadge = false,
            badgeNum = 0
        ),

        // Your deck nav bar item.
        NavItemState(
            title = "Your Deck",
            selectedIcon = Icons.Filled.Build,
            unselectedIcon = Icons.Outlined.Build,
            hasBadge = true,
            badgeNum = 25
        ),

        // Account nav bar item.
        NavItemState(
            title = "Account",
            selectedIcon = Icons.Filled.Face,
            unselectedIcon = Icons.Outlined.Face,
            hasBadge = true,
            badgeNum = 0
        ),
    )

    // Variable to remember the current bottom navigation state as it changes.
    var bottomNavState by rememberSaveable {
        mutableStateOf(0)
    }

    // Creation of the Scaffold container that needs an experimental api opt in.
    Scaffold(
        // Top bar that has a title, navigation and action icons.
        topBar = {
                // We assign the title as a box with modifiers and the actual text.
                 TopAppBar(
                     title = {
                         Box(
                             modifier.fillMaxWidth(),
                             contentAlignment = Alignment.Center
                             ){
                             Text(
                                 text = "Deck Builder",
                                 color = Color.Cyan,
                                 fontWeight = FontWeight.ExtraBold
                             )
                         }
                     },

                     // Modifier add some padding and round the edges of the top bar.
                     modifier
                         .padding(10.dp)
                         .clip(RoundedCornerShape(20.dp)),

                     // Navigation icon example with no implementation, placed at the start of the top bar.
                     navigationIcon = {
                         IconButton(onClick = { /*TODO*/ }) {
                             Icon(
                                 imageVector = Icons.Default.Menu,
                                 contentDescription = "Menu Icon")
                         }
                     },

                     // Action icon example with no implementation, placed at the end of the top bar.
                     actions = {
                         IconButton(onClick = { /*TODO*/ }) {
                             Icon(
                                 imageVector = Icons.Outlined.Search,
                                 contentDescription = "Search Icon"
                             )
                         }
                     },

                     // Set the top bar to a desired hex colour.
                     colors = TopAppBarDefaults.smallTopAppBarColors(
                         containerColor = Color(0xFFCCE6CC)
                     )
                 )
        },

        // Bottom bar that has navigation bar and items.
        bottomBar = {
            // Navigation bar container used to give the bottom bar shape, colour and padding.
            NavigationBar(
                modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp)),
                containerColor = Color(0xFFCCE6CC)
            ) {
                    // Loop through the item list we made to create each navigation bar item.
                    items.forEachIndexed{index, item ->
                        NavigationBarItem(
                            selected = bottomNavState == index,
                            onClick = { bottomNavState = index },
                            icon = {
                                // Check if the navigation bar item has a badge to display.
                                BadgedBox(badge = {
                                    if (item.hasBadge) Badge {}

                                    if (item.badgeNum != 0) Badge {
                                        Text(text = item.badgeNum.toString())
                                    }
                                }) {
                                     //Display the icon as either selected or unselected based on the current bottomNavState.
                                    Icon(
                                        imageVector = if (bottomNavState == index) item.selectedIcon
                                        else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            },

                            // Set the label of the icon to the title value of the item.
                            label = {
                                Text(text = item.title)
                            },

                            // Set the colours of the navigation bar items.
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF5B9A5B),
                                selectedTextColor = Color(0xFF5B9A5B),
                                indicatorColor = Color(0xFFADE1AD)
                            )
                        )
                    }
            }
        }
    ) { contentPadding ->
        // Create a column to hold the text in the center both horizontally and vertically.
        Column(
            modifier
                .padding(contentPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
       ) {
            /*
             Text held within the column in the center of the screen that pulls from the bottomNavState
             variable and displays the title of the current selected icon.
             */
            Text(
                text = items[bottomNavState].title,
                color = Color.Blue,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 44.sp
            )
        }
    }
}