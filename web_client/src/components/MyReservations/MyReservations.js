import React, { Component } from 'react';
import styles from './MyReservations.module.scss';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import * as RestClient from 'api/REST/RestClient';
import GraveCard from '../GraveCard/GraveCard';
import Grid from '@material-ui/core/Grid';
import moment from 'moment';
import Typography from '@material-ui/core/Typography';
import 'react-big-calendar/lib/css/react-big-calendar.css';

const localizer = momentLocalizer(moment);

class MyReservations extends Component {     

    state = {
        events : [],
        graves : []
    }

    componentDidMount() {
        this.getFunerals();
        this.getGraves();
    }

    getGraves = async () => {
        const graves = await RestClient.getGravesForUser(this.props.userId);
        if (graves) {
            this.setState({ graves: graves });
        }
    }

    getFunerals = async () => {
        const funerals = await RestClient.getFunerals();
        const myFunerals = (funerals && this.props.userId) ? funerals.filter(funeral => Number(funeral.userId) === this.props.userId) : [];
        if (funerals && myFunerals) {
            const funeralEvents = [];
            myFunerals.forEach(funeral => {
                const dateStr = funeral.date.substring(0,funeral.date.length-3);
                const date = new Date(dateStr);
                date.setMinutes(0);
                date.setMilliseconds(0);
                const endDate = new Date(dateStr);
                endDate.setMinutes(0);
                endDate.setMilliseconds(0);
                endDate.setHours(date.getHours() + 1);
                const event = { id: funeral.id, title: `Funeral reserved, grave ${funeral.grave.graveNumber}`, start: date, end: endDate }
                funeralEvents.push(event);
            });
            this.setState({events: funeralEvents});
        }
    }

    render() {
        return (
            <div className={styles.container}>
                <div className={styles.graveArea}>
                <Typography variant="h5" component="h2">My reserved graves</Typography>
                <Grid
                    container
                    direction="row"
                >
                { this.state.graves.map(grave => 
                    <GraveCard key={grave.graveNumber} {...grave} />) }
               </Grid>
               <div className={styles.padding}><Typography variant="h5" component="h2">My reserved funerals</Typography></div>
                </div>
                <div className={styles.calendarContainer}>
                    <Calendar
                        localizer={localizer}
                        events={this.state.events}
                        startAccessor="start"
                        endAccessor="end"
                        defaultView="agenda"
                        views={['agenda']}
                    />
                </div>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    userId: state.auth.userId
});  

export default withRouter(connect(mapStateToProps, undefined)(MyReservations));
