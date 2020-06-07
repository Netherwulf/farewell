import React, { Component } from 'react';
import styles from './Reserve.module.scss';
import DatePicker from 'react-datepicker';
import * as RestClient from 'api/REST/RestClient';
import { connect } from 'react-redux';
import "react-datepicker/dist/react-datepicker.css";
import Snackbar from '@material-ui/core/Snackbar';
import Alert from '@material-ui/lab/Alert';
import { withRouter, Link } from 'react-router-dom';
import Typography from '@material-ui/core/Typography';
import moment from 'moment';

const min = new Date().setHours(8);
const max = new Date().setHours(18);
const today = new Date();
const tomorrow = new Date(today);
tomorrow.setDate(tomorrow.getDate() + 2);
tomorrow.setHours(9);
tomorrow.setMinutes(0);
tomorrow.setSeconds(0);
tomorrow.setMilliseconds(0);

class Reserve extends Component {

    state = {
        date: tomorrow,
        excludedDates: [{
            dateId: new Date().getFullYear() + "" + new Date().getMonth() + "" + new Date().getDay(),
            dates: []
        }],
        availableFuneralDirectors: [],
        religions: [],
        selectedReligion: null,
        graves: [],
        gravesData: [],
        currentExcludedTimes: [],
        dateBirth: null,
        dateDeath: null,
        loaded: false,
        errorOpen: false,
        errorMessage: "",
        successOpen: false,
        successMessage: ""
    };
     
    handleChange = date => {
        const dateId = date.getFullYear() + "" + date.getMonth() + "" + date.getDay();
        const excludedDate = this.state.excludedDates.find(element => element.dateId === dateId);
        this.setState({
            date: date,
            currentExcludedTimes: excludedDate ? excludedDate.dates : [],
        });
    };

    componentDidMount() {
        this.getExcludedDates();
        this.getFuneralDirectors();
        this.getGraves();
    }

    handleSubmit = (event) => {
        event.preventDefault();
        const now = new Date();
        const funeral = {
            funeralDirectorId: event.target.elements.funeralDirector.value,
            reservationDate: moment(now).local().format('YYYY-MM-DD hh:mm:ss-00'),
            date: moment(this.state.date).local().format('YYYY-MM-DD hh:mm:ss-00'),
            userId: this.props.userId
        };
        const deceased = {
            surname: event.target.elements.lastName.value,
            name: event.target.elements.firstName.value,
            dateOfBirth: event.target.elements.birthDate.value,
            dateOfDeath: event.target.elements.deathDate.value,
            placeOfBirth: event.target.elements.birthPlace.value,
            placeOfDeath: event.target.elements.deathPlace.value,
        };
        const grave = this.state.gravesData.find(grave => grave.graveNumber ===  event.target.elements.grave.value);
        delete grave.id;
        if (deceased.surname && deceased.name && deceased.dateOfBirth && deceased.dateOfDeath && deceased.placeOfBirth && deceased.placeOfDeath)
            grave.deceased = [deceased];
        else
            return;
        funeral.grave = grave;
        this.postFuneral(funeral);
    }

    postFuneral = async (funeral) => {
        const postResult = await RestClient.postFuneral(funeral);
        if (!postResult) {
            this.setState({ errorOpen: true, errorMessage: "No response from server" });
        }
        else if (postResult.message) {
            this.setState({ errorOpen: true, errorMessage: postResult.message });
        }
        else {
            this.setState({ successMessage: "Reservation completed, proceed to payment", successOpen: true });
        }
    }

    getGraves = async () => {
        const graves = await RestClient.getGravesForUser(this.props.userId);
        const gravesData = await RestClient.getCemeteryGraves();
        if (graves && gravesData) {
            const availableGraves = graves.filter(grave => Number(grave.capacity) > grave.deceased.length);
            if (availableGraves.length)
                this.setState({ graves: availableGraves, gravesData: gravesData, loaded: true });
        } else {
            this.setState({ noGraves: "No graves reserved, you must reserve a grave first." });
        }
    }

    getFuneralDirectors = async () => {
        const funeralDirectors = await RestClient.getFuneralDirectors();
        if (funeralDirectors) {
            const religions = [...new Set(funeralDirectors.map(item => item.religion))];
            this.setState({ availableFuneralDirectors: funeralDirectors, religions: religions, selectedReligion: religions.length ? religions[0] : null });
        }
    }

    onChangeReligion = (event) => {
        if (event.target)
            this.setState({ selectedReligion: event.target.value });
    }

    handleClose = (event, reason) => {
        if (this.state.errorOpen)
            this.setState({ errorOpen: false });
        else {
            this.setState({ successOpen: false },
            this.props.history.push("/my-reservations"));
        }
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
            const dateId = tomorrow.getFullYear() + "" + tomorrow.getMonth() + "" + tomorrow.getDay();
            const excludedDate = excludedDates.find(element => element.dateId === dateId);
            this.setState({excludedDates: excludedDates, currentExcludedTimes: excludedDate ? excludedDate.dates : [], loadedAll: true});
        }
    }

    handleChange1 = date => {
        this.setState({
            dateBirth: date
        });
    };

    handleChange2 = date => {
        this.setState({
            dateDeath: date
        });
    };

    render() {
        const CustomInput = ({ value, onClick }) => (
            <div className="form-group" onClick={onClick}>
                    <label>Date</label>
                    <input type="text" id="date" required className="form-control" value={value} />
            </div>
        );
        const CustomInput1 = ({ value, onClick }) => (
            <div className="form-group" >
                    <label>Date of birth</label>
                    <input onClick={onClick} required type="text" id="birthDate" className="form-control" value={value} />
            </div>
        );
        const CustomInput2 = ({ value, onClick }) => (
            <div className="form-group" >
                    <label>Date of death</label>
                    <input onClick={onClick} required type="text" id="deathDate" className="form-control" value={value} />
            </div>
        );
        return (
            (this.state.loaded && this.state.loadedAll) ? <>
            <div className={styles.container}>
                <Snackbar anchorOrigin={{ vertical: 'top', horizontal: 'right' }} open={this.state.successOpen} autoHideDuration={1000} onClose={this.handleClose}>
                    <Alert onClose={this.handleClose} severity="success">
                        {this.state.successMessage}
                    </Alert>
                </Snackbar>
                <Snackbar anchorOrigin={{ vertical: 'top', horizontal: 'right' }} open={this.state.errorOpen} autoHideDuration={3000} onClose={this.handleClose}>
                    <Alert onClose={this.handleClose} severity="error">
                        {this.state.errorMessage}
                    </Alert>
                </Snackbar>
                <div className={styles.reserve}>
                <form onSubmit={this.handleSubmit}>
                    <Typography variant="h5" component="h2">Make a reservation</Typography>
                    <div className={styles.formItem}>
                        <DatePicker
                            selected={this.state.date}
                            onChange={this.handleChange}
                            required
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
                            customInput={<CustomInput />}
                        />
                    </div>
                    <div className="form-group">
                        <label>Grave { this.state.graves && !this.state.graves.length ? <span className={styles.info}>*</span> : <></> }</label>
                        { this.state.graves && !this.state.graves.length ? <div className={styles.info}>No graves available, reserve one first</div> : <></> }
                        <div className="input-group mb-3">
                        <div className="input-group-prepend">
                        <label className="input-group-text"></label>
                        </div>
                        <select disabled={this.state.graves.length ? false : true} className="custom-select" id="grave">
                            {this.state.graves.map(grave => <option value={grave.graveNumber} key={grave.graveNumber}>Grave number {grave.graveNumber} </option>)}
                        </select>
                        </div>
                    </div>
                    <div className="form-group">
                            <label>First name</label>
                            <input required className="form-control" id="firstName" type="text"></input>
                        </div>
                        <div className="form-group">
                            <label>Last name</label>
                            <input required className="form-control" id="lastName" type="text"></input>
                        </div>
                        <div className="form-group">
                            <label>Place of birth</label>
                            <input required className="form-control" id="birthPlace" type="text"></input>
                        </div>
                        <DatePicker
                                selected={this.state.dateBirth}
                                onChange={this.handleChange1}
                                dateFormat="yyyy-MM-dd"
                                showYearDropdown
                                maxDate={today}
                                inLine
                                customInput={<CustomInput1 />}
                            />
                        <div className="form-group">
                            <label>Place of death</label>
                            <input required className="form-control" id="deathPlace" type="text"></input>
                        </div>
                        <DatePicker
                                selected={this.state.dateDeath}
                                onChange={this.handleChange2}
                                dateFormat="yyyy-MM-dd"
                                showYearDropdown
                                maxDate={today}
                                inLine
                                customInput={<CustomInput2 />}
                   />
                    <div className="form-group">
                        <label>Religion</label>
                        <div className="input-group mb-3">
                        <div className="input-group-prepend">
                            <label className="input-group-text"></label>
                        </div>
                        <select required onChange={this.onChangeReligion} value={this.state.selectedReligion} className="custom-select" id="religion">
                        {this.state.religions.map(r => <option value={r} key={r}>{r}</option>)}
                        </select>
                        </div>
                    </div>
                    <div className="form-group">
                        <label>Funeral director</label>
                        <div className="input-group mb-3">
                        <div className="input-group-prepend">
                            <label className="input-group-text"></label>
                        </div>
                        <select required disabled={this.state.availableFuneralDirectors.filter(fd => fd.religion === this.state.selectedReligion).length ? false : true} className="custom-select" id="funeralDirector">
                        {this.state.availableFuneralDirectors.filter(fd => fd.religion === this.state.selectedReligion).map(fd => <option value={fd.id} key={fd.id}>{fd.name} {fd.surname}</option>)}
                        </select>
                        </div>
                    </div>
                    <div className={styles.buttonArea}>
                        <button type="submit" className="btn btn-dark btn-block">Confirm</button>
                    </div>
                    </form>
                </div>
            </div></> : <div className={styles.container}>
                        {this.state.noGraves ? <div>No graves reserved, <Link to="/graves">reserve a grave</Link> first.</div> : <></>}
                    </div>
        )
    }
}

const mapStateToProps = state => ({
    userId: state.auth.userId
});

export default withRouter(connect(mapStateToProps, undefined)(Reserve));
