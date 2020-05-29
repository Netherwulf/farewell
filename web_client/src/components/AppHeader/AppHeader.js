import React from 'react';
import styles from './AppHeader.module.scss';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import * as actions from '../../store/actions/actionsIndex';

function AppHeader(props) {
  return (
    <div className={styles.header}>
      <div className={styles.tabGroup}>
        <span className={styles.headerTitle} onClick={() => {props.history.push("/")}}><span role="img" aria-label="Icon">⚰️</span><span>Farewell</span></span>
        <div className={(props.history.location.pathname === '/funerals' || props.history.location.pathname === '/reserve-funeral') ? styles.tabActive : styles.tab} onClick={() => {props.history.push("/funerals")}}>Funerals</div>
        <div className={props.history.location.pathname === '/graves'? styles.tabActive : styles.tab } onClick={() => {props.history.push("/graves")}} >Graves</div>
        { props.isEmployee ? <div className={props.history.location.pathname === '/reports'? styles.tabActive : styles.tab } onClick={() => {props.history.push("/reports")}} >Reports</div> : <></>}
      </div>
      <div className={styles.tabGroup}>
        { props.isAuthenticated ? 
        <><div className={styles.navigable} onClick={() => {props.history.push("/my-reservations")}} ><span role="img" aria-label="AdminIcon">👤</span>My reservations</div>
        <div className={styles.navigable} onClick={() => {props.onLogout()}} ><span role="img" aria-label="ExitIcon">🚪</span>Log out</div>
        </> : 
        <div className={styles.navigable} onClick={() => {props.history.push("/login")}} ><span role="img" aria-label="Keycon">🔑</span> Log in</div> }
      </div>
    </div>
  );
}

const mapStateToProps = state => ({
  isAuthenticated: state.auth.isAuthenticated,
  isEmployee: true
});

const mapDispatchToProps = dispatch => ({
  onLogout: () => dispatch(actions.logout()),
});

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(AppHeader));
