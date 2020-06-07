import React, { Component } from 'react';
import styles from './Funerals.module.scss';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import { withRouter } from 'react-router-dom';
import * as RestClient from 'api/REST/RestClient';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';

const localizer = momentLocalizer(moment);

class Funerals extends Component {     

    state = {
        events : []
    }

    toReserve = () => {
        this.props.history.push("/reserve-funeral");
    }

    componentDidMount() {
        this.getFunerals();
    }

    getFunerals = async () => {
        const funerals = await RestClient.getFunerals();
        if (funerals) {
            const funeralEvents = [];
            funerals.forEach(funeral => {
                const d = funeral.date.substring(0,funeral.date.length - 3);
                const date = new Date(d);
                const endDate = new Date(d);
                endDate.setHours(date.getHours() + 1);
                const event = { id: funeral.id, title: "Reserved", start: date, end: endDate }
                funeralEvents.push(event);
            });
            this.setState({events: funeralEvents});
        }
    }

    render() {
        return (
            <div className={styles.container}>
                <div className={styles.calendarContainer}>
                    <Calendar
                        localizer={localizer}
                        events={this.state.events}
                        startAccessor="start"
                        endAccessor="end"
                        defaultView="week"
                        step={60}
                        timeslots={1}
                        views={['month', 'week', 'day']}
                        onSelectSlot={this.onSelect}
                    />
                </div>
                <div className={styles.buttonArea}>
                    <button type="submit" onClick={(this.toReserve)} className="btn btn-dark btn-block">Make a reservation</button>
                </div>   
             <div id="fb-root"></div>
                <script async defer crossorigin="anonymous" src="https://connect.facebook.net/pl_PL/sdk.js#xfbml=1&version=v7.0&appId=613587162699043&autoLogAppEvents=1"></script>
            </div>
        )
    }
}

export default withRouter(Funerals);
