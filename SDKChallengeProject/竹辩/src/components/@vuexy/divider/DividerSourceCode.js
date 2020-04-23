import React from "react"

export const dividerDefault = (
  <pre>
    <code className="language-jsx">
      {`
import React from "react"

class DividerDefault extends React.Component {

  render() {
    return(
      <div>
        <p>
          Ut enim ad minim veniam, quis nostrud exercitation ullamco
          laboris nisi ut aliquip ex ea commodo consequat. Duis aute
          irure dolor in reprehenderit in voluptate velit esse cillum
          dolore eu fugiat nulla pariatur. Excepteur sint occaecat
          cupidatat non proident, sunt in culpa qui officia deserunt
          mollit anim id est laborum.
        </p>
        <hr />
        <p>
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed
          do eiusmod tempor incididunt ut labore et dolore magna aliqua.
        </p>
      </div>
    )
  }
}
export default DividerDefault
`}
    </code>
  </pre>
)

export const dividerText = (
  <pre>
    <code className="language-jsx">
      {`
import React from "react"

class DividerText extends React.Component {

  render() {
    return(
      <div className="divider">
        <div className="divider-text">My Text</div>
      </div>
    )
  }
}
export default DividerText
`}
    </code>
  </pre>
)

export const dividerPosition = (
  <pre>
    <code className="language-jsx">
      {`
import React from "react"

class DividerPosition extends React.Component {

  render() {
    return(
      <div className="divider divider-left">
        <div className="divider-text">Left</div>
      </div>

      <div className="divider divider-left-center">
        <div className="divider-text">Left Center</div>
      </div>

      <div className="divider">
        <div className="divider-text">Center(Default)</div>
      </div>

      <div className="divider divider-right-center">
        <div className="divider-text">Right Center</div>
      </div>

      <div className="divider divider-right">
        <div className="divider-text">Right</div>
      </div>
    )
  }
}
export default DividerPosition
`}
    </code>
  </pre>
)

export const dividerColors = (
  <pre>
    <code className="language-jsx">
      {`
import React from "react"

class DividerColors extends React.Component {

  render() {
    return(
      <div className="divider">
        <div className="divider-text">Default</div>
      </div>
      <div className="divider divider-primary">
        <div className="divider-text">Primary</div>
      </div>
      <div className="divider divider-success">
        <div className="divider-text">Success</div>
      </div>
      <div className="divider divider-danger">
        <div className="divider-text">Danger</div>
      </div>
      <div className="divider divider-info">
        <div className="divider-text">Info</div>
      </div>
      <div className="divider divider-warning">
        <div className="divider-text">Warning</div>
      </div>
      <div className="divider divider-light">
        <div className="divider-text">Light</div>
      </div>
      <div className="divider divider-dark">
        <div className="divider-text">Dark</div>
      </div>
    )
  }
}
export default DividerColors
`}
    </code>
  </pre>
)

export const dividerIcon = (
  <pre>
    <code className="language-jsx">
      {`
import React from "react"
import {
  ArrowDown,
  Star,
  Check,
  XCircle,
  Clock
} from "react-feather"
class DividerIcons extends React.Component {

  render() {
    return(
      <div className="divider divider-left">
        <div className="divider-text">
          <ArrowDown />
        </div>
      </div>

      <div className="divider divider-left-center">
        <div className="divider-text">
          <Star />
        </div>
      </div>

      <div className="divider">
        <div className="divider-text">
          <Check />
        </div>
      </div>

      <div className="divider divider-right-center">
        <div className="divider-text">
          <XCircle />
        </div>
      </div>

      <div className="divider divider-right">
        <div className="divider-text">
          <Clock />
        </div>
      </div>
    )
  }
}
export default DividerIcons
`}
    </code>
  </pre>
)

export const dividerStyle = (
  <pre>
    <code className="language-jsx">
      {`
import React from "react"

class DividerStyle extends React.Component {

  render() {
    return(
      <div>
      <div className="divider divider-dotted">
        <div className="divider-text">Dotted</div>
      </div>

      <div className="divider divider-dashed">
        <div className="divider-text">Dashed</div>
      </div>

      <div className="divider">
        <div className="divider-text">Solid</div>
      </div>
      </div>
    )
  }
}
export default DividerStyle
`}
    </code>
  </pre>
)
