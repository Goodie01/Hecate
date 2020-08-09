import React from 'react';
import Col from 'react-bootstrap/Col'
import Row from 'react-bootstrap/Row'
import Container from "react-bootstrap/Container";

const List = (props) => {
    const {repos: logs} = props;
    if (!logs) return <p>No logs, sorry</p>;
    else if (logs.length === 0) return <p>Empty logs, sorry</p>;
    else return (
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
                        <Col>{log.request.path}</Col>
                        <Col>{log.response.body}</Col>
                    </Row>
                );
            })}
        </Container>
    );
};
export default List;