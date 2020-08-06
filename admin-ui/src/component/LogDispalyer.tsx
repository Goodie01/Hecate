import React from 'react';
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import Container from "react-bootstrap/Container";

const List = (props) => {
    const {repos: logs} = props;
    if (!logs || logs.length === 0) return <p>No logs, sorry</p>;
    return (
        <Container>
            <Row>
                <Col>listener ID</Col>
                <Col>Time</Col>
                <Col>Request</Col>
                <Col>Response</Col>
            </Row>
            {logs.map((log) => {
                return (
                    <Row>
                        <Col>{log.listenerId}</Col>
                        <Col>{log.time}</Col>
                        <Col>{log.request}</Col>
                        <Col>{log.response}</Col>
                    </Row>
                );
            })}
        </Container>
    );
};
export default List;