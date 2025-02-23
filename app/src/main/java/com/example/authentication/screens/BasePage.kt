import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.authentication.AppScreens
import com.example.authentication.NotesViewModel
import kotlinx.coroutines.launch
import np.com.bimalkafle.firebaseauthdemoapp.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasePage(
    navController: NavController,
    title: String,
    content: @Composable () -> Unit,
    fab: (@Composable () -> Unit)? = null,
    authViewModel: AuthViewModel,
    topBarActions: @Composable () -> Unit = {}
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val colors = MaterialTheme.colorScheme

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                drawerShape = DrawerDefaults.shape,
                drawerContainerColor = colors.primaryContainer
            ) {
                LazyColumn {
//                    item {
//                        Card(
//                            modifier = Modifier.fillMaxSize()
//                                .padding(16.dp)
//                                .height(120.dp),
//                            colors = CardDefaults.cardColors(containerColor = colors.secondary)
//                        ) {
//                            DrawerOption(title = "", color = colors.onSecondary) {
//                                scope.launch { drawerState.close() }
//                                navController.navigate(AppScreens.LoginScreen.route)
//                            }
//                        }
//                    }

                    item{
//                        Divider(color = colors.onSurface, thickness = 1.dp)
                        DrawerOption(title = "Profile", color = colors.onPrimaryContainer) {
                            scope.launch { drawerState.close() }
                        }
                    }

                    item{
                        Divider(color = colors.onSurface, thickness = 1.dp)
                        DrawerOption(title = "Theme", color = colors.onPrimaryContainer) {
                            scope.launch { drawerState.close() }
                        }
                    }
                    item{
                        Divider(color = colors.onSurface, thickness = 1.dp)
                        DrawerOption(title = "Settings", color = colors.onPrimaryContainer) {
                            scope.launch { drawerState.close() }
                        }
                    }

                    item{
                        Divider(color = colors.onSurface, thickness = 1.dp)
                        DrawerOption(title = "Privacy Policy", color = colors.onPrimaryContainer) {
                            scope.launch { drawerState.close() }
                        }
                    }

                    item {
                        Divider(color = colors.onSurface, thickness = 1.dp)

                        DrawerOption(title = "Sign Out", color = colors.onPrimaryContainer) {
                            scope.launch { drawerState.close() }
                            authViewModel.signout()
                            navController.navigate(AppScreens.LoginScreen.route) {
                                popUpTo(AppScreens.HomeScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }

                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title, color = colors.onPrimaryContainer) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colors.primaryContainer
                    ),
                    actions = {topBarActions?.invoke()}
                    ,

                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu Button",
                                tint = colors.onPrimaryContainer
                            )
                        }
                    }
                )
            }
            ,
            floatingActionButton = { fab?.invoke() }
        ) { ScreenPadding ->
            Column(modifier = Modifier.padding(ScreenPadding)) {
                content()
            }
        }
    }
}

@Composable
fun DrawerOption(title: String, color: androidx.compose.ui.graphics.Color, onItemClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Text(
        text = title,
        modifier = Modifier
            .padding(24.dp)
            .clickable { onItemClick() },
        fontSize = 24.sp,
        color = colors.onPrimaryContainer
    )
}
