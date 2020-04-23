import React from "react"
import classnames from "classnames"
import { Badge } from "reactstrap"
class Avatar extends React.Component {
  render() {
    return (
      <div
        className={classnames(
          `avatar ${this.props.color ? `bg-${this.props.color}` : null}  ${
            this.props.className
          }`,
          {
            "avatar-sm": this.props.size && this.props.size === "sm",
            "avatar-lg": this.props.size && this.props.size === "lg",
            "avatar-xl": this.props.size && this.props.size === "xl"
          }
        )}
      >
        {this.props.img === false || this.props.img === undefined ? (
          <span
            className={classnames("avatar-content", {
              "position-relative": this.props.badgeUp
            })}
          >
            {this.props.content ? this.props.content : null}

            {this.props.icon ? (
              <div className="avatar-icon">{this.props.icon}</div>
            ) : null}
            {this.props.badgeUp ? (
              <Badge
                color={
                  this.props.badgeColor ? this.props.badgeColor : "primary"
                }
                className="badge-sm badge-up"
                pill
              >
                {this.props.badgeText ? this.props.badgeText : "0"}
              </Badge>
            ) : null}
          </span>
        ) : (
          <img
            src={this.props.img}
            alt="avatarImg"
            height={
              this.props.imgHeight && !this.props.size
                ? this.props.imgHeight
                : 32
            }
            width={
              this.props.imgWidth && !this.props.size ? this.props.imgWidth : 32
            }
          />
        )}
        {this.props.status ? (
          <span className={`avatar-status-${this.props.status}`}></span>
        ) : null}
      </div>
    )
  }
}
export default Avatar
