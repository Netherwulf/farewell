import React from 'react';
import { Redirect, Route, Switch, withRouter } from 'react-router-dom';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { connect } from 'react-redux';
import CssBaseline from '@material-ui/core/CssBaseline';
import Layout from './hoc/Layout/Layout';
import Announcements from './components/Announcements/Announcements';
import Funerals from './components/Funerals/Funerals';
import Graves from './components/Graves/Graves';
import MyReservations from './components/MyReservations/MyReservations';
import Reserve from './components/Reserve/Reserve';
import ReserveGrave from './components/ReserveGrave/ReserveGrave';
import Login from './components/Login/Login';
import Register from './components/Register/Register';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import * as actions from './store/actions/actionsIndex';
import Reports from './components/Reports/Reports';

class App extends React.Component {

  render() {

    const nonAuthenticatedRoutes = (
      <Switch>
        <Route path='/login' component={Login} />
        <Route path='/register' component={Register} />
        <Route path='/' component={Announcements} />
        <Redirect to='/login' />
      </Switch>
    );

    const authenticatedRoutes = (
      <Switch>
        <Route path='/funerals' component={Funerals} />
        <Route path='/graves' component={Graves} />
        <Route path='/reserve-funeral' component={Reserve} />
        <Route path='/reserve-grave' component={ReserveGrave} />
        <Route path='/my-reservations' component={MyReservations} />
        <Route path='/reports' component={Reports} />
        <Route path='/logout' component={Announcements} />
        <Route path='/' exact component={Announcements} />
        <Redirect to='/' />
      </Switch>
    );
    
    const employeeRoutes = (
      <Switch>
        <Route path='/funerals' component={Funerals} />
        <Route path='/graves' component={Graves} />
        <Route path='/reserve-funeral' component={Reserve} />
        <Route path='/reserve-grave' component={ReserveGrave} />
        <Route path='/my-reservations' component={MyReservations} />
        <Route path='/logout' component={Announcements} />
        <Route path='/' exact component={Announcements} />
        <Redirect to='/' />
      </Switch>
    );
    
    return (
      <CssBaseline>
        <Layout>
          { this.props.isAuthenticated ? authenticatedRoutes : nonAuthenticatedRoutes }
        </Layout>
      </CssBaseline>
    );
  }
}

const mapStateToProps = state => ({
  isAuthenticated: state.auth.isAuthenticated
});

const mapDispatchToProps = dispatch => ({
  onLogin: () => dispatch(actions.login()),
});

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(App));
