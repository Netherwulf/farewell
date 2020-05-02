import React, { Component } from 'react';
import styles from './Login.module.scss';
import LoginForm from './LoginForm';

class Login extends Component {

    render() {
        return (
            <div className={styles.container}>
                <LoginForm/>
            </div>
        )
    }
}

export default Login;
