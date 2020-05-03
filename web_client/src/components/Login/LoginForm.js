import React, { Component } from "react";
import { connect } from 'react-redux';
import * as actions from '../../store/actions/actionsIndex';
import styles from './LoginForm.module.scss';
import Snackbar from '@material-ui/core/Snackbar';
import Alert from '@material-ui/lab/Alert';

class LoginForm extends Component {

    state = {
        errorOpen: false,
        errorMessage: "Incorrect email or password"
    }

    handleSubmit = (event) => {
        event.preventDefault();
        const userData = {
            email: event.target.elements.email.value,
            password: event.target.elements.password.value
        }
        this.handleLogin(userData);
    }

    handleLogin = async (userData) => {
        await this.props.onLogin(userData.email, userData.password);
        if (!this.props.isAuthenticated) {
            this.setState({ errorOpen: true });
        }
    }

    handleClose = (event, reason) => {
        this.setState({ errorOpen: false });
    }

    render() {
        return (
            <>
            <Snackbar anchorOrigin={{ vertical: 'top', horizontal: 'right' }} open={this.state.errorOpen} autoHideDuration={3000} onClose={this.handleClose}>
                <Alert onClose={this.handleClose} severity="error">
                    {this.state.errorMessage}
                </Alert>
            </Snackbar>
            <div className={styles.authWrapper}><div className={styles.authInner}>
                <form onSubmit={this.handleSubmit}>
                    <h2>Sign In</h2>
                    <div className="form-group">
                        <label>Email address</label>
                        <input type="email" id="email" required className="form-control" placeholder="Enter email" />
                    </div>
                    <div className="form-group">
                        <label>Password</label>
                        <input type="password" id="password" required className="form-control" placeholder="Enter password" />
                    </div>
                    <div className="form-group">
                        <div className="custom-control custom-checkbox">
                            <input type="checkbox" className="custom-control-input" id="customCheck1" />
                            <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                        </div>
                    </div>
                    <button type="submit" className="btn btn-primary btn-block">Submit</button>
                    <p className={styles.register}>
                        Need to <a href="/register">register?</a>
                    </p>
                </form>
            </div></div>
            </>
        );
    }
}

const mapStateToProps = state => ({
    isAuthenticated: state.auth.isAuthenticated
});  

const mapDispatchToProps = dispatch => ({
    onLogin: (email, password) => dispatch(actions.login(email, password)),
});

export default connect(mapStateToProps, mapDispatchToProps)(LoginForm);
