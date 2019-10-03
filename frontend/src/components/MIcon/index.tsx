import React from 'react'
import { Icon } from 'antd'
import icons from '@/icons/index.ts'

const MIcon = ({ type, style = {}, ...props }) => {
    const isCustom = Object.keys(icons).includes(type)
    return <Icon type={isCustom || type} style={style} component={isCustom && icons[type]} {...props} />
}

export default MIcon
