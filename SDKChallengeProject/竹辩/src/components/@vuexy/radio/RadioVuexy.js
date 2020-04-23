import React from "react"
import classnames from "classnames"
class RadioVuexy extends React.Component {
  render() {
    return (
      <div
        className={classnames(
          `vx-radio-con ${this.props.className} vx-radio-${this.props.color}`
        )}
      >
        <input
          type="radio"
          defaultChecked={this.props.defaultChecked}
          value={this.props.value}
          disabled={this.props.disabled}
          name={this.props.name}
          onClick={this.props.onClick}
          onChange={this.props.onChange}
          ref={this.props.ref}
          checked={this.props.checked}
        />
        <span
          className={classnames("vx-radio", {
            "vx-radio-sm": this.props.size === "sm",
            "vx-radio-lg": this.props.size === "lg"
          })}
        >
          <span className="vx-radio--border" />
          <span className="vx-radio--circle" />
        </span>
        <span>{this.props.label}</span>
      </div>
    )
  }
}
export default RadioVuexy
