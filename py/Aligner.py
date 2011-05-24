#!/usr/bin/env python
# -*- coding: utf-8 -*-


class Aligner(object):
    """ generated source for Aligner

    """
    INCR = 0.01f

    @classmethod
    def alignToObstacle(cls, m):
        while not Collider.collidesLevel(m):
            if m.canJump():
                m.setY(m.getY() + cls.INCR)
            else:
                m.setY(m.getY() - cls.INCR)
        if m.canJump():
            m.setY(m.getY() - cls.INCR)
        else:
            m.setY(m.getY() + cls.INCR)


