import React, { Component } from 'react';
import styles from './ReserveGrave.module.scss';
import * as RestClient from 'api/REST/RestClient';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import Typography from '@material-ui/core/Typography';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import Snackbar from '@material-ui/core/Snackbar';
import Alert from '@material-ui/lab/Alert';
import moment from 'moment';
import 'moment-timezone';

const today = new Date();

class ReserveGrave extends Component {

    state = {
        graves: [],
        dateBirth: null,
        dateDeath: null,
        errorOpen: false,
        errorMessage: "",
        successOpen: false,
        successMessage: ""
    };

    handleSubmit = (event) => {
        event.preventDefault();
        /*const deceased = {
            surname: event.target.elements.firstName.value,
            name: event.target.elements.lastName.value,
            dateOfBirth: event.target.elements.birthDate.value,
            dateOfDeath: event.target.elements.deathDate.value,
            placeOfBirth: event.target.elements.birthPlace.value,
            placeOfDeath: event.target.elements.deathPlace.value,
        };*/
        const grave = this.state.graves.find(grave => grave.graveNumber ==  event.target.elements.grave.value);
        /*if (deceased.surname && deceased.name && deceased.dateOfBirth && deceased.dateOfDeath && deceased.placeOfBirth && deceased.placeOfDeath)
            grave.deceased.push(deceased);*/
        const now = new Date();
        grave.reservationDate = moment(now).local().format('YYYY-MM-DD hh:mm:ss-00');
        grave.userId = this.props.userId;
        this.postGrave(grave);
    }

    componentDidMount() {
        this.getGraves();
    }

    postGrave = async (grave) => {
        const postResult = await RestClient.postGrave(grave);
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
        const graves = await RestClient.getCemeteryGraves();
        if (graves) {
            this.setState({ graves: graves.filter(grave => grave.userId === null) });
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
    }

    handleClose = (event, reason) => {
        if (this.state.errorOpen)
            this.setState({ errorOpen: false });
        else {
            this.setState({ successOpen: false },
            this.props.history.push("/my-reservations"));
        }
    }

    render() {
        const CustomInput1 = ({ value, onClick }) => (
            <div className="form-group" >
                    <label>Date of birth</label>
                    <input onClick={onClick} type="text" id="birthDate" className="form-control" value={value} />
            </div>
        );
        const CustomInput2 = ({ value, onClick }) => (
            <div className="form-group" >
                    <label>Date of death</label>
                    <input onClick={onClick} type="text" id="deathDate" className="form-control" value={value} />
            </div>
        );
        return (
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
                        </div>
                        <div className="form-group">
                            <label className={styles.bigger}>Grave</label>
                            <div className="input-group mb-3">
                            <div className="input-group-prepend">
                            <label className="input-group-text"></label>
                            </div>
                            <select className="custom-select" id="grave">
                                {this.state.graves.map(grave => <option value={grave.graveNumber} selected={this.props.location.state && this.props.location.state.graveNumber === grave.graveNumber } key={grave.graveNumber}>Grave number {grave.graveNumber} </option>)}
                            </select>
                            </div>
                        </div>
                        {/* <label className={styles.bigger}>Optional deceased data</label>
                        <div className="form-group">
                            <label>First name</label>
                            <input className="form-control" id="firstName" type="text"></input>
                        </div>
                        <div className="form-group">
                            <label>Last name</label>
                            <input className="form-control" id="lastName" type="text"></input>
                        </div>
                        <div className="form-group">
                            <label>Place of birth</label>
                            <input className="form-control" id="birthPlace" type="text"></input>
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
                            <input className="form-control" id="deathPlace" type="text"></input>
                        </div>
                        <DatePicker
                                selected={this.state.dateDeath}
                                onChange={this.handleChange2}
                                dateFormat="yyyy-MM-dd"
                                showYearDropdown
                                maxDate={today}
                                inLine
                                customInput={<CustomInput2 />}
                   />*/}
                        <div className={styles.buttonArea}>
                            <button type="submit" className="btn btn-dark btn-block">Confirm</button>
                        </div>
                    </form>
                </div>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    userId: state.auth.userId
});

export default withRouter(connect(mapStateToProps, undefined)(ReserveGrave));
