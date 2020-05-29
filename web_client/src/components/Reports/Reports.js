import React, { Component } from 'react';
import styles from './Reports.module.scss';
import { withRouter } from 'react-router-dom';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import { VictoryChart, VictoryBar, VictoryGroup, VictoryHistogram } from 'victory';

class Reports extends Component {     

    render() {
        return (
            <div className={styles.container}>
                <Grid
                    container
                    direction="row"
                    justify="center"
                    alignItems="center"
                >
                    <div className={styles.chartSection}>
                        <Typography variant="h5" component="h2">
                        Number of graves per user
                        </Typography>
                        <VictoryChart>
                        <VictoryHistogram
                            style={{ data: { fill: '#8B0046' }}}
                            cornerRadius={3}
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
                        </VictoryChart>
                    </div>
                    <div className={styles.chartSection}>
                        <VictoryChart
                        domain={{ y: [0.5, 5.5] }}
                        >
                            <VictoryGroup horizontal
                            offset={10}
                            style={{ data: { width: 6 } }}
                            colorScale={["brown", "tomato", "gold"]}
                            >
                            <VictoryBar
                                data={[
                                { x: 1, y: 1 },
                                { x: 2, y: 2 },
                                { x: 3, y: 3 },
                                { x: 4, y: 2 },
                                { x: 5, y: 1 }
                                ]}
                            />
                            <VictoryBar
                                data={[
                                { x: 1, y: 2 },
                                { x: 2, y: 3 },
                                { x: 3, y: 4 },
                                { x: 4, y: 5 },
                                { x: 5, y: 5 }
                                ]}
                            />
                            <VictoryBar
                                data={[
                                { x: 1, y: 1 },
                                { x: 2, y: 2 },
                                { x: 3, y: 3 },
                                { x: 4, y: 4 },
                                { x: 5, y: 4 }
                                ]}
                            />
                        </VictoryGroup>
                        </VictoryChart>
                    </div>
                </Grid>
                <Grid
                    container
                    direction="row"
                    justify="center"
                    alignItems="center"
                >
                    <div className={styles.chartSection}>
                        <Typography variant="h5" component="h2">
                        Number of funerals per user
                        </Typography>
                        <VictoryChart>
                        <VictoryHistogram
                            style={{ data: { fill: '#8B0046' }}}
                            cornerRadius={3}
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
                        </VictoryChart>
                    </div>
                    <div className={styles.sectionSummary}>
                        <p>Average graves per day: 1.1</p>
                        <p>Average graves per month: 2.3</p>
                        <p>Average graves per year: 8.2</p>
                    </div>
                    <div className={styles.sectionSummary}>
                        <p>Median graves per day: 1.1</p>
                        <p>Median graves per month: 2.3</p>
                        <p>Median graves per year: 8.2</p>
                    </div>
                </Grid>
            </div>
        )
    }
}

export default withRouter(Reports);
