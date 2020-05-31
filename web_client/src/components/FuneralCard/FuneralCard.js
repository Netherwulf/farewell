import React, { Component } from 'react';
import styles from './FuneralCard.module.scss';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Collapse from '@material-ui/core/Collapse';
import Snackbar from '@material-ui/core/Snackbar';
import Alert from '@material-ui/lab/Alert';
import Credentials from './apiGoogleconfig.json'
import moment from 'moment';
import { TwitterTimelineEmbed, TwitterShareButton, TwitterFollowButton, TwitterHashtagButton, TwitterMentionButton, TwitterTweetEmbed, TwitterMomentShare, TwitterDMButton, TwitterVideoEmbed, TwitterOnAirButton } from 'react-twitter-embed';

class FuneralCard extends Component {

    state = {
        open: false,
        errorOpen: false,
        errorMessage: "",
        successOpen: false,
        successMessage: ""
    }

    gapi = null;

    componentDidMount() {
        this.getGapi();
    }

    handleClick = (event) => {
        this.setState({open: !this.state.open});
    }

    getGapi = () => {
        var script = document.createElement("script");
        script.src = "https://apis.google.com/js/api.js";
        document.body.appendChild(script);
        script.onload = () => {
            this.gapi = window['gapi'];
            this.gapi.load('client:auth2', () => this.initGoogleClient());
        };
    }

    initGoogleClient = () => {
      this.gapi.client.init({
        apiKey: Credentials.apiKey,
        clientId: Credentials.clientId,
        discoveryDocs: Credentials.discoveryDocs,
        scope: Credentials.scope
      });
    }

    handleGoogleCalendarResponse = (result) => {

      const url = 'data:image/png;base64,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'
      if (!result) {
        this.setState({ errorOpen: true, errorMessage: "No response from server" });
      }
      else if (result.message) {
          this.setState({ errorOpen: true, errorMessage: result.message });
      }
      else {
          this.setState({ successMessage: "Funeral succesfully added to Google Calendar", successOpen: true });
      }
    }

    handleClose = () => {
      if (this.state.errorOpen)
          this.setState({ errorOpen: false });
      else {
          this.setState({ successOpen: false });
      }
  }

    addToGoogleCalendar = () => {
      const decedent = this.props.grave.deceased.sort((d1, d2) => moment(this.props.date).diff(d2.dateOfDeath, 'days') - moment(this.props.date).diff(d1.dateOfDeath, 'days'))[0]
        this.gapi.client.load('calendar', 'v3')
        this.gapi.auth2.getAuthInstance().signIn().then(() => {
            var event = {
                'summary': decedent.name + ' ' + decedent.surname + ' - Funeral',
                'start': {
                  'dateTime': moment(this.props.date).format('YYYY-MM-DDTHH:mm:SS'),
                  'timeZone': 'Poland'
                },
                'end': {
                  'dateTime': moment(this.props.date).add(1, 'hours').format('YYYY-MM-DDTHH:mm:SS'),
                  'timeZone': 'Poland'
                },
              };
            this.gapi.client.calendar.events.insert({
                'calendarId': 'primary',
                'resource': event
            }).execute((response) => this.handleGoogleCalendarResponse(response))
        });
    }

    render() {
        const deceased = this.props.grave.deceased.sort((d1, d2) => moment(this.props.date).diff(d2.dateOfDeath, 'days') - moment(this.props.date).diff(d1.dateOfDeath, 'days'))[0];
        return (
            <Card className={styles.root} variant="outlined">
            <Snackbar anchorOrigin={{ vertical: 'top', horizontal: 'right' }} open={this.state.successOpen} autoHideDuration={3000} onClose={this.handleClose}>
            <Alert onClose={this.handleClose} severity="success">
                {this.state.successMessage}
            </Alert>
            </Snackbar>
            <Snackbar anchorOrigin={{ vertical: 'top', horizontal: 'right' }} open={this.state.errorOpen} autoHideDuration={3000} onClose={this.handleClose}>
                <Alert onClose={this.handleClose} severity="error">
                    {this.state.errorMessage}
                </Alert>
            </Snackbar>
            <CardContent>
                <Typography className={styles.title} color="textSecondary" gutterBottom>
                { moment(this.props.date).format('MMMM Do YYYY, HH:mm') }
                </Typography>
                <Typography variant="h5" component="h2">
                { deceased.name } { deceased.surname }
                </Typography>
                <Typography className={styles.pos} color="textSecondary">
                { moment(deceased.dateOfBirth).format('MMMM Do YYYY') } - { moment(deceased.dateOfDeath).format('MMMM Do YYYY') }
                </Typography>
            </CardContent>
            <CardActions>
                <Button size="small" onClick={this.handleClick}>Details</Button>
                <TwitterShareButton
                data-size="large"
                url={'w'}
                options={{ text: deceased.name + ' ' + deceased.surname + ' - Funeral' + '\nDied: ' + moment(deceased.dateOfDeath).format('d MMMM YYYY') + '\nThe funeral ceremony will take place on ' + moment(this.props.date).format('Do MMMM YYYY, HH:mm') + '\n' , via: 'farewell'}}
                />
            </CardActions>
            <Collapse in={this.state.open} timeout="auto" unmountOnExit>
            <CardContent>
            <Typography>
                Funeral director: {this.props.funeralDirectorId}
            </Typography>
            <div className={styles.googleCalendarArea}>
                <button type="submit" className="btn btn-info btn-block" onClick={this.addToGoogleCalendar}>Add to Google Calendar</button>
            </div>
            </CardContent>
            </Collapse>
            </Card>
        )
    }
}

export default FuneralCard;
