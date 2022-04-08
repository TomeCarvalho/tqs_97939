import logo from './logo.svg';
import './App.css';
import {AppBar, Box, IconButton, Toolbar, Typography, useMediaQuery} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import * as React from 'react';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';

function App() {
    const prefersDarkMode = useMediaQuery('(prefers-color-scheme: dark)');

    const theme = React.useMemo(
        () =>
            createTheme({
                palette: {
                    mode: prefersDarkMode ? 'dark' : 'light',
                },
            }),
        [prefersDarkMode],
    );

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <Box sx={{flexGrow: 1}}>
                <AppBar position="static">
                    <Toolbar>
                        <Typography variant="h6" component="div" sx={{flexGrow: 1}}>
                            COVID-19 Statistics
                        </Typography>
                    </Toolbar>
                </AppBar>
            </Box>
        </ThemeProvider>
    );
}

export default App;
