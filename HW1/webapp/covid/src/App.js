import "./App.css";
import {
    Alert,
    AppBar, Autocomplete,
    Box, Grid,
    TextField,
    Toolbar,
    Typography,
    useMediaQuery
} from "@mui/material";
import * as React from "react";
import {createTheme, ThemeProvider} from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import {useEffect, useState} from "react";
import Stack from '@mui/material/Stack';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import TimePicker from '@mui/lab/TimePicker';
import DateTimePicker from '@mui/lab/DateTimePicker';
import DesktopDatePicker from '@mui/lab/DesktopDatePicker';
import MobileDatePicker from '@mui/lab/MobileDatePicker';

function App() {
    const prefersDarkMode = useMediaQuery("(prefers-color-scheme: dark)");

    const theme = React.useMemo(
        () =>
            createTheme({
                palette: {
                    mode: prefersDarkMode ? "dark" : "light",
                },
            }),
        [prefersDarkMode],
    );

    const [countries, setCountries] = useState([]);
    const [selCountry, setSelCountry] = useState();
    const [countryInfo, setCountryInfo] = useState([]);
    const [date, setDate] = useState(new Date());

    // useEffect(() => {
    //     console.log("countryInfo", countryInfo);
    // }, [countryInfo])

    useEffect(() => {
        console.log("selCountry:", selCountry)
        fetch(`http://localhost:8080/api/history?country=${selCountry}&date=${date}`, {
            headers: {Accept: "application/json", "Content-Type": "application/json"}
        })
            .then(response => response.json())
            .then(data => {
                setCountryInfo(data.response);
            })
            .catch(err => {
                console.log(err);
            })
    }, [selCountry])

    useEffect(() => {
        fetch("http://localhost:8080/api/countries", {
            headers: {Accept: 'application/json', 'Content-Type': 'application/json'}
        })
            .then(response => response.json())
            .then(data => {
                setCountries(data.response);
            })
            .catch(err => {
                console.log(err);
            })
    }, [])

    const onChangeCountry = (event, newValue) => {
        if (newValue != null)
            setSelCountry(newValue);
    }

    const onChangeDate = (newValue) => {
        setDate(newValue);
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
                renderInput={(params) => <TextField {...params} label="Country/Region"/>}
                onChange={onChangeCountry}
                sx={{m: 2}}
            />
            <Box sx={{m: 2}}>
                <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <MobileDatePicker
                        label="Date"
                        inputFormat="dd/MM/yyyy"
                        value={date}
                        onChange={onChangeDate}
                        renderInput={(params) => <TextField {...params} />}
                        disableFuture={true}
                        sx={{m: 2}}
                    />
                </LocalizationProvider>
            </Box>


            {(countryInfo.length)
                ?
                <p>Estudaste</p>
                :
                <Alert variant="outlined" severity="info" sx={{m: 2}}>No region selected.</Alert>
            }
        </ThemeProvider>
    );
}

export default App;
