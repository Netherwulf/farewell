import React, { Component } from 'react';
import styles from './Reports.module.scss';
import { withRouter } from 'react-router-dom';
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

    componentDidMount() {
        const funeralData = {"averageReservationToPurchaseTime":30.409,"averageFuneralsPerDay":1.196,"medianFuneralsPerDay":1.0,"modeFuneralsPerDay":1,"averageFuneralsPerMonth":36.281,"medianFuneralsPerMonth":37.0,"modeFuneralsPerMonth":37,"averageFuneralsPerYear":290.25,"medianFuneralsPerYear":296.0,"modeFuneralsPerYear":306,"funeralsPerFuneralDirector":{"22":41,"23":32,"24":43,"25":30,"26":24,"27":26,"28":37,"29":15,"10":18,"11":38,"12":45,"13":49,"14":52,"15":18,"16":48,"17":48,"18":51,"19":22,"1":27,"2":48,"3":24,"4":33,"5":23,"6":42,"7":29,"8":21,"9":20,"20":27,"21":36},"averageFuneralsPerFuneralDirector":33.345,"medianFuneralsPerFuneralDirector":32.0,"modeFuneralsPerFuneralDirector":48,"funeralsPerUser":{"88":12,"89":15,"110":8,"111":9,"112":11,"113":7,"114":6,"115":8,"116":8,"90":14,"117":8,"118":10,"91":13,"92":16,"119":8,"93":15,"94":25,"95":11,"96":13,"97":16,"98":16,"99":16,"120":7,"121":5,"122":8,"123":6,"124":4,"125":5,"126":8,"127":3,"128":3,"129":4,"130":8,"131":8,"132":5,"133":1,"134":2,"135":4,"136":2,"137":3,"138":4,"139":4,"141":2,"142":5,"143":1,"144":4,"145":4,"147":2,"148":1,"40":11,"41":16,"42":13,"43":10,"44":13,"45":11,"46":11,"47":12,"48":14,"49":20,"150":2,"152":1,"156":1,"157":1,"50":9,"51":16,"52":10,"53":20,"54":7,"55":11,"56":16,"57":13,"58":11,"59":13,"60":15,"61":18,"62":11,"63":18,"64":13,"65":17,"66":16,"67":13,"68":15,"69":11,"70":22,"71":9,"72":15,"73":5,"74":12,"75":19,"76":7,"77":15,"78":13,"79":12,"100":12,"101":8,"102":16,"103":11,"104":9,"105":14,"106":8,"80":15,"107":13,"108":10,"81":16,"109":7,"82":13,"83":11,"84":21,"85":18,"86":30,"87":13},"averageFuneralsPerUser":10.459,"medianFuneralsPerUser":11.0,"modeFuneralsPerUser":13};
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
            funeralsPerUserData: funeralsPerUser,
            funeralsDaily: funeralsDaily,
            funeralsMonthly: funeralsMonthly,
            funeralsYearly: funeralsYearly,
            time: funeralData.averageReservationToPurchaseTime
        });
    }

    render() {
        console.log(this.state.funeralsPerUserData);
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
                            /*labels={
                                ({ datum }) => `${this.state.funeralDirectors.find(funeralDirector => funeralDirector.id == datum.x).name} ${this.state.funeralDirectors.find(funeralDirector => funeralDirector.id == datum.x).surname}`
                            }*/
                            labels={({ datum }) => datum.y}
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
