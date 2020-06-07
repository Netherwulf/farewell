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
        time: 0,
        gravesDaily: [],
        gravesMonthly: [],
        gravesYearly: [],
        deceasedPerGrave: [],
        gravesTime: 0,
        deceasedPerGrave: [],
        gravesPerUser: [],
        gravesPerUserData: [],
        deceasedPerGraveData: []
    }

    getFuneralReport = async () => {
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

            const funeralDirectorsPerReligion = [];

            const religions = [...new Set(funeralDirectors.map(item => item.religion))];
            religions.forEach(religion => {
                const fds = funeralDirectors.filter(fd => fd.religion === religion)
                funeralDirectorsPerReligion.push({ religion: religion, funeralDirectors: fds});
            });

            const funeralsPerReligion = [];
            funeralDirectorsPerReligion.forEach(religion => {
                let funeralSum = 0;
                console.log(religion);
                religion.funeralDirectors.forEach(fd => {
                    const id = Number(fd.id);
                    const number = funeralData.funeralsPerFuneralDirector[id];
                    console.log(number);
                    if (!isNaN(number))
                        funeralSum += number;
                });
                funeralsPerReligion.push({ x: religion.religion, y: funeralSum });
            });
            console.log(funeralsPerReligion);

            this.setState({ 
                funeralsPerFuneralDirectorData: funeralsPerFuneralDirector,
                funeralDirectors: funeralDirectors,
                funeralsPerUserData: funeralsPerUser,
                funeralsDaily: funeralsDaily,
                funeralsMonthly: funeralsMonthly,
                funeralsYearly: funeralsYearly,
                time: funeralData.averageReservationToPurchaseTime,
                funeralsPerReligionData: funeralsPerReligion
            });
        }
    }

    /*getFuneralReport2 = async () => {
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
    }*/

    getGraveReport = async () => {
        const graveData = await RestClient.getGraveReport();

        if (graveData) {

            const gravesPerUserData = graveData.gravesPerUser.map((i) => { 
                return { x: i }});

            const deceasedPerGraveData = graveData.deceasedPerGrave.map((i) => {
                return { x: i }});

            const gravesDaily = [
                { x: "average", y: graveData.averageGravesPerDay },
                { x: "median", y: graveData.medianFGravesPerDay },
                { x: "mode", y: graveData.modeGravesPerDay }
            ];
            const gravesMonthly = [
                { x: "average", y: graveData.averageGravesPerMonth },
                { x: "median", y: graveData.medianGravesPerMonth },
                { x: "mode", y: graveData.modeGravesPerMonth }
            ];
            const gravesYearly = [
                { x: "average", y: graveData.averageGravesPerYear },
                { x: "median", y: graveData.medianGravesPerYear },
                { x: "mode", y: graveData.modeGravesPerYear }
            ];

            const deceasedPerGrave = [
                { x: "average", y: graveData.averageDeceasedPerGrave  },
                { x: "median", y: graveData.medianDeceasedPerGrave },
                { x: "mode", y: graveData.modeDeceasedPerGrave }
            ];
            const gravesPerUser = [
                { x: "average", y: graveData.averageGravesPerUser },
                { x: "median", y: graveData.medianGravesPerUser },
                { x: "mode", y: graveData.modeGravesPerUser }
            ];

            this.setState({ 
                gravesDaily: gravesDaily,
                gravesMonthly: gravesMonthly,
                gravesYearly: gravesYearly,
                gravesTime: graveData.averageReservationToPurchaseTime,
                deceasedPerGrave: deceasedPerGrave,
                gravesPerUser: gravesPerUser,
                gravesPerUserData: gravesPerUserData,
                deceasedPerGraveData: deceasedPerGraveData.filter(obj => obj.x < 30)
            });
        }
    }

    componentDidMount() {
        this.getFuneralReport();
        this.getGraveReport();
    }

    getTimeString(value) {
        var time = value * 60;
        var minutes = Math.floor(time / 60);
        var seconds = Math.floor(time - minutes * 60);
        return `${minutes} mins ${seconds} s`
    }

    render() {
        return (
            <div className={styles.container}>
                <div id="users" className={styles.sectionTitle}><span><AnchorLink href='#users'>User statistics</AnchorLink></span></div>
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
                            bins={6}
                            data={this.state.gravesPerUserData}
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
                            bins={15}
                            data={this.state.deceasedPerGraveData}
                        />
                        <VictoryAxis
                            tickValues={[5,10,15,20,25,30]}
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
                        domain={{ y: [0, 50] }}
                        >
                            <VictoryGroup horizontal
                            offset={15}
                            style={{ data: { width: 10 } }}
                            colorScale={["brown", "tomato", "gold"]}
                            >
                            <VictoryBar
                                data={this.state.gravesYearly}
                            />
                            <VictoryBar
                                data={this.state.gravesMonthly}
                            />
                            <VictoryBar
                                data={this.state.gravesDaily}
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
                    <div className={styles.box}><Typography variant="h5">{this.getTimeString(this.state.time)}</Typography></div>
                </div>
                <div className={styles.chartSection}>
                <Typography variant="h6">Grave reservation payment time</Typography>
                    <div className={styles.box}><Typography variant="h5">{this.getTimeString(this.state.gravesTime)}</Typography></div>
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
                        {
                            this.state.funeralDirectors.length ?
                        <VictoryBar
                            data={this.state.funeralsPerFuneralDirectorData}
                            labels={
                                ({ datum }) => {
                                    const fd = this.state.funeralDirectors.find(funeralDirector => funeralDirector.id === Number(datum.x));
                                    return fd ? `${fd.name} ${fd.surname}: ${datum.y}` : datum.x;
                                }
                            }
                            labelComponent={<VictoryTooltip/>}
                        /> :
                        <VictoryBar
                            data={this.state.funeralsPerFuneralDirectorData}
                            labels={
                                ({ datum }) => datum.x
                            }
                            labelComponent={<VictoryTooltip/>}
                        /> 
                        }
                        </VictoryChart>
                    </div>
                </Grid>
                <Grid
                    container
                    direction="row"
                    justify="start"
                >
                    <div className={styles.chartSectionExtra}>
                        <Typography variant="h6">Funerals per religion</Typography>
                        <VictoryChart domainPadding={{ x: 30 }} domain={{ y: [0, 200] }} height={300} width={800} >
                        <VictoryBar
                            data={this.state.funeralsPerReligionData}
                            style={{ data: { fill: "#c43a31" } }}
                            labels={
                                ({ datum }) => datum.y
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
