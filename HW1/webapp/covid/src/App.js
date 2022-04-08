import logo from './logo.svg';
import './App.css';
import {
    AppBar, Autocomplete,
    Box,
    FormControl,
    IconButton,
    InputLabel, MenuItem,
    Select, TextField,
    Toolbar,
    Typography,
    useMediaQuery
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import * as React from 'react';
import {createTheme, ThemeProvider} from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import {useEffect, useState} from "react";

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

    const [countries, setCountries] = useState([]);
    const [selCountry, setSelCountry] = useState();

    useEffect(() => {
        console.log("selCountry:", selCountry)
    }, [selCountry])

    useEffect(() => {
        fetch(`http://localhost:8080/api/countries`, {
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                // console.log("data" + data.response);
                setCountries(data.response);
            })
            .catch(err => {
                console.log(err);
            })
    }, [])

    const onChangeCountry = (event, newValue) => {
        console.log("newValue:", newValue);
        if (newValue != null) {
            setSelCountry(newValue);
        }
    }

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline/>
            <Box sx={{flexGrow: 1}}>
                <AppBar position="static">
                    <Toolbar>
                        <Typography variant="h6" component="div" sx={{flexGrow: 1}}>
                            COVID-19 Statistics
                        </Typography>
                    </Toolbar>
                </AppBar>
            </Box>
            <Autocomplete
                disablePortal
                id="autocomplete-countries"
                options={countries}
                renderInput={(params) => <TextField {...params} label="Country" />}
                onChange={onChangeCountry}
                sx={{m: 2}}
            />
        </ThemeProvider>
    );
}

export default App;
