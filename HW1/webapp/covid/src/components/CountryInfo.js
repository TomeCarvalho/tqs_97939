import React from "react";
import {Box, Grid, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";

const CountryInfo = ({info}) => {
    return (
        <Box sx={{m: 2}}>
            <TableContainer component={Paper}>
                <Table aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell/>
                            <TableCell align="left">Cases</TableCell>
                            <TableCell align="left">Deaths</TableCell>
                            <TableCell align="left">Tests</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        <TableRow>
                            <TableCell>New</TableCell>
                            <TableCell>{info["cases"]["new"]}</TableCell>
                            <TableCell>{info["deaths"]["new"]}</TableCell>
                            <TableCell>{info["tests"]["new"]}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>Per 1M Population</TableCell>
                            <TableCell>{info["cases"]["1M_pop"]}</TableCell>
                            <TableCell>{info["deaths"]["1M_pop"]}</TableCell>
                            <TableCell>{info["tests"]["1M_pop"]}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>Total</TableCell>
                            <TableCell>{info["cases"]["total"]}</TableCell>
                            <TableCell>{info["deaths"]["total"]}</TableCell>
                            <TableCell>{info["tests"]["total"]}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>Active</TableCell>
                            <TableCell>{info["cases"]["active"]}</TableCell>
                            <TableCell>-</TableCell>
                            <TableCell>-</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>Critical</TableCell>
                            <TableCell>{info["cases"]["critical"]}</TableCell>
                            <TableCell>-</TableCell>
                            <TableCell>-</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell>Recovered</TableCell>
                            <TableCell>{info["cases"]["recovered"]}</TableCell>
                            <TableCell>-</TableCell>
                            <TableCell>-</TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    )
}

export default CountryInfo