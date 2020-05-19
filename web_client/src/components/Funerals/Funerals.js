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
                const date = new Date(funeral.date);
                const endDate = new Date(funeral.date);
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
            </div>
        )
    }
}

export default withRouter(Funerals);
