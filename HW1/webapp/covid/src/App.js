import "./App.css";
import {
    Alert,
    AppBar, Autocomplete,
    Box, CircularProgress, Grid, TextField,
    Toolbar,
    Typography,
    useMediaQuery
} from "@mui/material";
import * as React from "react";
import {createTheme, ThemeProvider} from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";
import {useEffect, useState} from "react";
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import MobileDatePicker from '@mui/lab/MobileDatePicker';
import CountryInfo from "./components/CountryInfo";

function App() {
    const apiUrl = 'http://localhost:8080/api'

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

    useEffect(() => {
        if (selCountry !== undefined && date !== undefined) {
            document.title = `COVID-19 Statistics: ${selCountry}`
            setCountryInfo(undefined);
            fetch(`${apiUrl}/history?country=${selCountry}&day=${date.toISOString().substring(0, 10)}`, {
                headers: {Accept: "application/json", "Content-Type": "application/json"}
            })
                .then(response => response.json())
                .then(data => {
                    setCountryInfo(data.response);
                })
                .catch(err => {
                    console.log(err);
                })
        }
    }, [selCountry, date])

    useEffect(() => {
        document.title = "COVID-19 Statistics"
        fetch(`${apiUrl}/countries`, {
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
            <Grid container>
                <Grid item xs={6}>
                    <Autocomplete
                        disablePortal
                        id="autocomplete-countries"
                        options={countries}
                        renderInput={(params) => <TextField {...params} label="Country/Region"/>}
                        onChange={onChangeCountry}
                        sx={{m: 2}}
                    />
                </Grid>
                <Grid item xs={6}>
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
                </Grid>
            </Grid>

            {(countryInfo !== undefined && countryInfo.length)
                ?
                <CountryInfo info={countryInfo[0]}/>
                :
                <>
                    {(selCountry === undefined)
                        ?
                        <Alert variant="outlined" severity="info" sx={{m: 2}}>No region selected.</Alert>
                        :
                        <Grid container justifyContent="center">
                            <CircularProgress/>
                        </Grid>
                    }
                </>
            }
        </ThemeProvider>
    );
}

export default App;
