import React, { Component } from 'react';
import styles from './Reports.module.scss';
import { withRouter } from 'react-router-dom';
import * as RestClient from 'api/REST/RestClient';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import { VictoryChart, VictoryBar, VictoryGroup, VictoryHistogram, VictoryLegend, VictoryTooltip, VictoryLabel, VictoryAxis, VictoryPie } from 'victory';
import AnchorLink from 'react-anchor-link-smooth-scroll';

class Reports extends Component {

    state = {
        open: false,
        funeralsPerFuneralDirectorData: [],
        funeralDirectors: [],
        funeralsPerUserData: [],
        funeralsDaily: [],
        funeralsMonthly: [],
        funeralsYearly: [],
        time: 0
    }

    getFuneralReportBack = async () => {
        const funeralDirectors = await RestClient.getFuneralDirectors();
        const funeralData = await RestClient.getFuneralReport();
        if (funeralDirectors && funeralData) {
            const funeralsPerFuneralDirector = Object.keys(funeralData.funeralsPerFuneralDirector).map((i) => { 
                return { x: i, y: funeralData.funeralsPerFuneralDirector[i] }});
            const funeralsPerUser = Object.keys(funeralData.funeralsPerUser).map((i) => { 
                return { x: funeralData.funeralsPerUser[i] }});
            const funeralsDaily = [
                { x: "average", y: funeralData.averageFuneralsPerDay },
                { x: "median", y: funeralData.medianFuneralsPerDay },
                { x: "mode", y: funeralData.modeFuneralsPerDay }
            ];
            const funeralsMonthly = [
                { x: "average", y: funeralData.averageFuneralsPerMonth },
                { x: "median", y: funeralData.medianFuneralsPerMonth },
                { x: "mode", y: funeralData.modeFuneralsPerMonth }
            ];
            const funeralsYearly = [
                { x: "average", y: funeralData.averageFuneralsPerYear },
                { x: "median", y: funeralData.medianFuneralsPerYear },
                { x: "mode", y: funeralData.modeFuneralsPerYear }
            ];
            this.setState({ 
                funeralsPerFuneralDirectorData: funeralsPerFuneralDirector,
                funeralDirectors: funeralDirectors,
                funeralsPerUserData: funeralsPerUser,
                funeralsDaily: funeralsDaily,
                funeralsMonthly: funeralsMonthly,
                funeralsYearly: funeralsYearly,
                time: funeralData.averageReservationToPurchaseTime
            });
        }
    }

    getFuneralReport = async () => {
        const graveData = await RestClient.getGraveReport();
        const funeralData = await RestClient.getFuneralReport();
        if (funeralData) {
            const funeralsPerFuneralDirector = Object.keys(funeralData.funeralsPerFuneralDirector).map((i) => { 
                return { x: i, y: funeralData.funeralsPerFuneralDirector[i] }});
            const funeralsPerUser = Object.keys(funeralData.funeralsPerUser).map((i) => { 
                return { x: funeralData.funeralsPerUser[i] }});
            const funeralsDaily = [
                { x: "average", y: funeralData.averageFuneralsPerDay },
                { x: "median", y: funeralData.medianFuneralsPerDay },
                { x: "mode", y: funeralData.modeFuneralsPerDay }
            ];
            const funeralsMonthly = [
                { x: "average", y: funeralData.averageFuneralsPerMonth },
                { x: "median", y: funeralData.medianFuneralsPerMonth },
                { x: "mode", y: funeralData.modeFuneralsPerMonth }
            ];
            const funeralsYearly = [
                { x: "average", y: funeralData.averageFuneralsPerYear },
                { x: "median", y: funeralData.medianFuneralsPerYear },
                { x: "mode", y: funeralData.modeFuneralsPerYear }
            ];
            this.setState({ 
                funeralsPerFuneralDirectorData: funeralsPerFuneralDirector,
                funeralDirectors: [],
                funeralsPerUserData: funeralsPerUser,
                funeralsDaily: funeralsDaily,
                funeralsMonthly: funeralsMonthly,
                funeralsYearly: funeralsYearly,
                time: funeralData.averageReservationToPurchaseTime
            });
        }
    }

    componentDidMount() {
        this.getFuneralReport();
    }

    render() {
        //console.log(this.state.funeralsPerUserData);
        return (
            <div className={styles.container}>
                <div id="users" className={styles.sectionTitle}><span className><AnchorLink href='#users'>User statistics</AnchorLink></span></div>
                <Grid
                    container
                    direction="row"
                    justify="start"
                    alignItems="center"
                >
                    <div className={styles.chartSection}>
                        <Typography variant="h6">Graves per user</Typography>
                        <VictoryChart>
                        <VictoryHistogram
                            style={{ data: { fill: "#5D001E", stroke: "white" }}}
                            bins={[0,1,2,3,4,5,6,7,8,9,10]}
                            data={[
                            { x: 0 },
                            { x: 1 },
                            { x: 1 },
                            { x: 1 },
                            { x: 1 },
                            { x: 2 },
                            { x: 2 },
                            { x: 3 },
                            { x: 4 },
                            { x: 7 },
                            { x: 7 },
                            { x: 10 }
                            ]}
                        />
                        <VictoryAxis
                            tickLabelComponent={<VictoryLabel style={{textAnchor:'end', fontSize: '12px'}}/>}
                        />
                        <VictoryAxis
                            dependentAxis={true}
                            tickLabelComponent={<VictoryLabel style={{fontSize: '12px'}}/>}
                        />
                        </VictoryChart>
                    </div>
                    <div className={styles.chartSection}>
                        <Typography variant="h6">Funerals per user</Typography>
                        <VictoryChart>
                        <VictoryHistogram
                            style={{ data: { fill: "brown", stroke: "white" }}}
                            bins={10}
                            data={this.state.funeralsPerUserData}
                        />
                        <VictoryAxis
                            tickLabelComponent={<VictoryLabel style={{textAnchor:'end', fontSize: '12px'}}/>}
                        />
                        <VictoryAxis
                            dependentAxis={true}
                            tickLabelComponent={<VictoryLabel style={{fontSize: '12px'}}/>}
                        />
                        </VictoryChart>
                    </div>
                    <div className={styles.chartSection}>
                        <Typography variant="h6">Deceased per grave</Typography>
                        <VictoryChart>
                        <VictoryHistogram
                            style={{ data: { fill: "tomato", stroke: "white" }}}
                            bins={10}
                            data={[
                            { x: 0 },
                            { x: 1 },
                            { x: 1 },
                            { x: 1 },
                            { x: 1 },
                            { x: 2 },
                            { x: 2 },
                            { x: 3 },
                            { x: 4 },
                            { x: 7 },
                            { x: 7 },
                            { x: 10 }
                            ]}
                        />
                        <VictoryAxis
                            tickValues={[0,1,2,3,4,5,6,7,8,9,10]}
                            tickLabelComponent={<VictoryLabel style={{textAnchor:'end', fontSize: '12px'}}/>}
                        />
                        <VictoryAxis
                            dependentAxis={true}
                            tickLabelComponent={<VictoryLabel style={{fontSize: '12px'}}/>}
                        />
                        </VictoryChart>
                    </div>
                </Grid>
                <div id="reservations" className={styles.sectionExtra}><span className><AnchorLink href='#reservations'>Reservation statistics</AnchorLink></span></div>
                <Grid
                    container
                    direction="row"
                    justify="start"
                    alignItems="center"
                >
                    <div className={styles.chartSection}>
                        <Typography variant="h6">Number of funerals reserved</Typography>
                        <VictoryChart
                        padding={{ top: 40, bottom: 50, left: 70, right: 80 }}
                        >
                        <VictoryGroup horizontal
                            offset={15}
                            style={{ data: { width: 10 } }}
                            colorScale={["brown", "tomato", "gold"]}
                            >
                            <VictoryBar
                                data={this.state.funeralsYearly}
                            />
                            <VictoryBar
                                data={this.state.funeralsMonthly}
                            />
                            <VictoryBar
                                data={this.state.funeralsDaily}
                            />
                        </VictoryGroup>
                        </VictoryChart>
                    </div>
                    <div className={styles.chartWithLegend}>
                    <div className={styles.chartSection}>
                        <Typography variant="h6">Number of graves reserved</Typography>
                        <VictoryChart
                        padding={{ top: 40, bottom: 50, left: 70, right: 80 }}
                        domain={{ y: [0.5, 4.5] }}
                        >
                            <VictoryGroup horizontal
                            offset={15}
                            style={{ data: { width: 10 } }}
                            colorScale={["brown", "tomato", "gold"]}
                            >
                            <VictoryBar
                                data={[
                                { x: "daily", y: 1 },
                                { x: "monthly", y: 2 },
                                { x: "yearly", y: 3 },
                                ]}
                            />
                            <VictoryBar
                                data={[
                                { x: "daily", y: 2 },
                                { x: "monthly", y: 3 },
                                { x: "yearly", y: 4 },
                                ]}
                            />
                            <VictoryBar
                                data={[
                                { x: "daily", y: 1 },
                                { x: "monthly", y: 2 },
                                { x: "yearly", y: 3 },
                                ]}
                            />
                        </VictoryGroup>
                        </VictoryChart>
                    </div>
                    <div className={styles.legend}>
                    <VictoryLegend x={0} y={225}
                        title="Legend"
                        centerTitle
                        orientation="horizontal"
                        gutter={20}
                        style={{ border: { stroke: "black" }, title: {fontSize: 20 } }}
                        data={[
                        { name: "Per day", symbol: { fill: "gold" } },
                        { name: "Per month", symbol: { fill: "tomato" } },
                        { name: "Per year", symbol: { fill: "brown" } }
                        ]}
                    />
                    </div>
                    </div>
                </Grid>
                <Grid
                    container
                    direction="row"
                    justify="start"
                >
                <div className={styles.chartSection}>
                <Typography variant="h6">Funeral reservation payment time</Typography>
                    <div className={styles.box}><Typography variant="h5">{this.state.time.toFixed(0)} mins</Typography></div>
                </div>
                <div className={styles.chartSection}>
                <Typography variant="h6">Grave reservation payment time</Typography>
                    <div className={styles.box}><Typography variant="h5">{this.state.time.toFixed(0)} mins</Typography></div>
                </div>
                </Grid>
                <div id="funeraldirectors" className={styles.sectionExtra}>
                    <span className><AnchorLink href='#funeraldirectors'>Funeral director statistics</AnchorLink></span>
                </div>
                <Grid
                    container
                    direction="row"
                    justify="start"
                >
                    <div className={styles.chartSectionExtra}>
                        <Typography variant="h6">Funerals per funeral director</Typography>
                        <VictoryChart domainPadding={{ x: 10 }} height={300} width={800} >
                        <VictoryBar
                            data={this.state.funeralsPerFuneralDirectorData}
                            labels={
                                //({ datum }) => `${this.state.funeralDirectors.find(funeralDirector => funeralDirector.id === datum.x).name} ${this.state.funeralDirectors.find(funeralDirector => funeralDirector.id === datum.x).surname}`
                                ({ datum }) => datum.x
                            }
                            labelComponent={<VictoryTooltip/>}
                        />
                        </VictoryChart>
                    </div>
                </Grid>
            </div>
        )
    }
}

export default withRouter(Reports);
