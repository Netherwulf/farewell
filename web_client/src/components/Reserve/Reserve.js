import React, { Component } from 'react';
import styles from './Reserve.module.scss';
import DatePicker from 'react-datepicker';
import * as RestClient from 'api/REST/RestClient';
import "react-datepicker/dist/react-datepicker.css";
import Typography from '@material-ui/core/Typography';

const min = new Date().setHours(8);
const max = new Date().setHours(18);
const today = new Date();
const tomorrow = new Date(today);
tomorrow.setDate(tomorrow.getDate() + 1);

class Reserve extends Component {

    state = {
        date: tomorrow,
        excludedDates: [{
            dateId: new Date().getFullYear() + "" + new Date().getMonth() + "" + new Date().getDay(),
            dates: []
        }],
        currentExcludedTimes: []
    };
     
    handleChange = date => {
        const dateId = date.getFullYear() + "" + date.getMonth() + "" + date.getDay();
        const excludedDate = this.state.excludedDates.find(element => element.dateId === dateId);
        this.setState({
            date: date,
            currentExcludedTimes: excludedDate ? excludedDate.dates : []
        });
    };

    componentDidMount() {
        this.getExcludedDates();
    }

    getExcludedDates = async () => {
        const funerals = await RestClient.getFunerals();
        if (funerals) {
            const excludedDates = [];
            funerals.forEach(funeral => {
                const date = new Date(funeral.date);
                date.setMinutes(0);
                date.setSeconds(0);
                date.setMilliseconds(0);
                const dateId = date.getFullYear() + "" + date.getMonth() + "" + date.getDay();
                if (excludedDates.find(element => element.dateId === dateId))
                    excludedDates.find(element => element.dateId === dateId).dates.push(date);
                else {
                    excludedDates.push({ dateId: dateId, dates: [date]})
                }
            });
            this.setState({excludedDates: excludedDates});
        }
    }

    render() {
        const ExampleCustomInput = ({ value, onClick }) => (
            <div className="form-group" onClick={onClick}>
                    <label>Date</label>
                    <input type="text" id="date" required className="form-control" value={value} />
            </div>
        );
        return (
            <div className={styles.container}>
                <div className={styles.reserve}>
                    <Typography variant="h5" component="h2">Make a reservation</Typography>
                    <div className={styles.formItem}>
                        <DatePicker
                            selected={this.state.date}
                            onChange={this.handleChange}
                            showTimeSelect
                            timeFormat="HH:mm"
                            timeIntervals={60}
                            timeCaption="time"
                            dateFormat="MMMM d, yyyy h:mm aa"
                            minTime={min}
                            maxTime={max}
                            minDate={tomorrow}
                            inLine
                            excludeTimes={this.state.currentExcludedTimes}
                            customInput={<ExampleCustomInput />}
                        />
                    </div>
                    <div className="form-group">
                        <label>Funeral director</label>
                        <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="inputGroupSelect01"></label>
                        </div>
                        <select class="custom-select" id="inputGroupSelect01">
                            <option selected>Available on this date</option>
                            <option value="1">One</option>
                            <option value="2">Two</option>
                            <option value="3">Three</option>
                        </select>
                        </div>
                    </div>
                    <div className={styles.buttonArea}>
                        <button type="submit" className="btn btn-dark btn-block">Confirm</button>
                    </div>     
                </div>
            </div>
        )
    }
}

export default Reserve;