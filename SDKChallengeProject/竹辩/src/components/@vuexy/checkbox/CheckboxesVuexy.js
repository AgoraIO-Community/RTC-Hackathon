import React from "react"
class CheckBoxesVuexy extends React.Component {
  render() {
    return (
      <div
        className={`vx-checkbox-con ${
          this.props.className ? this.props.className : ""
        } vx-checkbox-${this.props.color}`}
      >
        <input
          type="checkbox"
          defaultChecked={this.props.defaultChecked}
          checked={this.props.checked}
          value={this.props.value}
          disabled={this.props.disabled}
          onClick={this.props.onClick ? this.props.onClick : null}
          onChange={this.props.onChange ? this.props.onChange : null}
        />
        <span
          className={`vx-checkbox vx-checkbox-${
            this.props.size ? this.props.size : "md"
          }`}
        >
          <span className="vx-checkbox--check">{this.props.icon}</span>
        </span>
        <span>{this.props.label}</span>
      </div>
    )
  }
}

export default CheckBoxesVuexy
