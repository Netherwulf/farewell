import React, { Component } from 'react';
import styles from './FuneralCard.module.scss';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Collapse from '@material-ui/core/Collapse';
import moment from 'moment';
import { TwitterTimelineEmbed, TwitterShareButton, TwitterFollowButton, TwitterHashtagButton, TwitterMentionButton, TwitterTweetEmbed, TwitterMomentShare, TwitterDMButton, TwitterVideoEmbed, TwitterOnAirButton } from 'react-twitter-embed';

class FuneralCard extends Component {

    state = {
        open: false
    }

    handleClick = (event) => {
        this.setState({open: !this.state.open});
    }

    render() {
        const deceased = this.props.grave.deceased.sort((d1, d2) => moment(this.props.date).diff(d2.dateOfDeath, 'days') - moment(this.props.date).diff(d1.dateOfDeath, 'days'))[0];
        return (
            <Card className={styles.root} variant="outlined">
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
            {/* <div class="fb-share-button" data-href="" data-layout="button" data-size="large"><a target="_blank" href="https://www.facebook.com/sharer/sharer.php?u=https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2Fplugins%2F&amp;src=sdkpreparse" class="fb-xfbml-parse-ignore">Udostępnij</a></div> */}
            </CardContent>
            </Collapse>
            </Card>
        )
    }
}

export default FuneralCard;
