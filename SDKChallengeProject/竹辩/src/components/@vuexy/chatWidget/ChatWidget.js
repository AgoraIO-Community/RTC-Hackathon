import React from "react"
import ReactDOM from "react-dom"
import PerfectScrollbar from "react-perfect-scrollbar"
import {
  Input,
  Button,
  Card,
  CardBody,
  CardHeader,
  CardTitle
} from "reactstrap"
import { Send } from "react-feather"
import senderImg from "../../../assets/img/portrait/small/avatar-s-2.jpg"
import receiverImg from "../../../assets/img/portrait/small/avatar-s-5.jpg"
class ChatWidget extends React.Component {
  state = {
    chatsList: [
      {
        msg: "Cake sesame snaps cupcake gingerbread",
        isSent: true
      },
      {
        msg: "Apple pie pie jujubes chupa chups muffin",
        isSent: false
      },
      {
        msg: "Chocolate cake",
        isSent: true
      },
      {
        msg: "Donut sweet pie oat cake dragée fruitcake",
        isSent: false
      },
      {
        msg: "Liquorice chocolate bar jelly beans icing",
        isSent: true
      },
      {
        msg: "Powder toffee tootsie roll macaroon cupcake",
        isSent: false
      },
      {
        msg:
          "Apple pie oat cake brownie cotton candy cupcake chocolate bar dessert",
        isSent: true
      },
      {
        msg: "Biscuit cake jujubes carrot cake topping sweet cake",
        isSent: false
      }
    ],
    msg: ""
  }

  componentDidMount() {
    this.scrollToBottom()
  }

  componentDidUpdate() {
    this.scrollToBottom()
  }

  scrollToBottom = () => {
    const chatContainer = ReactDOM.findDOMNode(this.chatArea)
    chatContainer.scrollTop = chatContainer.scrollHeight
  }

  render() {
    const { chatsList, msg } = this.state

    let renderChatList = chatsList.map((chat, i) => {
      return (
        <div
          key={i}
          className={`chat ${chat.isSent ? "chat-right" : "chat-left"}`}
        >
          <div className="chat-avatar">
            <div className="avatar m-0">
              <img
                src={chat.isSent ? senderImg : receiverImg}
                alt="chat avatar"
                height="40"
                width="40"
              />
            </div>
          </div>
          <div className="chat-body">
            <div className="chat-content">
              <p>{chat.msg}</p>
            </div>
          </div>
        </div>
      )
    })

    return (
      <Card className="chat-application chat-widget">
        <CardHeader>
          <CardTitle>Chat</CardTitle>
        </CardHeader>
        <div className="chat-app-window">
          <PerfectScrollbar
            className="user-chats widget-user-chat"
            options={{
              wheelPropagation: false
            }}
            ref={el => {
              this.chatArea = el
            }}
          >
            <div className="chats">{renderChatList}</div>
          </PerfectScrollbar>
          <div className="chat-footer">
            <CardBody className="d-flex justify-content-around">
              <Input
                className="mr-50"
                placeholder="Type your message..."
                value={msg}
                onChange={e =>
                  this.setState({
                    msg: e.target.value
                  })
                }
              />
              <Button
                className="btn-icon"
                color="primary"
                onClick={() => this.setState({ msg: "" })}
              >
                <Send size={15} />
              </Button>
            </CardBody>
          </div>
        </div>
      </Card>
    )
  }
}

export default ChatWidget
