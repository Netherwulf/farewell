import React, { Component } from 'react';
import styles from './Announcements.module.scss';
import * as RestClient from 'api/REST/RestClient';
import FuneralCard from '../FuneralCard/FuneralCard';
import Grid from '@material-ui/core/Grid';
import moment from 'moment';

class Announcements extends Component {

    state = {
        funerals: [
        ],
    }

    componentDidMount() {
        this.getFunerals();
    }

    getFunerals = async () => {
        const funerals = await RestClient.getFunerals();
        if (funerals) {
            const sortedFunerals = funerals.sort((d1, d2) => moment(d1.date).diff(d2.date, 'minutes'));
            this.setState({ funerals: sortedFunerals });
        }
    }

    render() {
        return (
            <div className={styles.container}>
                <Grid
                    container
                    direction="row"
                    justify="center"
                    alignItems="center"
                >
                { this.state.funerals.filter(funeral => funeral.grave && funeral.grave.deceased).map(funeral => <FuneralCard key={funeral.id} {...funeral} />) }
               </Grid>
            </div>
        )
    }
}

export default Announcements;
